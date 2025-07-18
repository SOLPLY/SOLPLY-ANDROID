package com.teamsolply.solply.maps

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.teamsolply.solply.maps.model.CoursePlace
import com.teamsolply.solply.maps.model.CourseSaveEntity
import com.teamsolply.solply.maps.model.CourseSaveType
import com.teamsolply.solply.maps.repository.MapsRepository
import com.teamsolply.solply.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class MapsViewModel @Inject constructor(
    private val mapsRepository: MapsRepository
) :
    BaseViewModel<MapsState, MapsIntent, MapsSideEffect>(MapsState()) {

    override fun handleIntent(intent: MapsIntent) {
        when (intent) {
            // Add Place
            is MapsIntent.AddPlaceClick -> reduce { copy(startAddMyCourse = intent.addPlace) }

            is MapsIntent.SelectedCourseForPlace -> {
                filterSelectedCourseCount(intent.courseId)
            }

            is MapsIntent.SavePlaceInMyCourse -> {
                val selectedCourseId = currentState.isAddMyCourseSelected
                val selectedCourseName =
                    currentState.courses.firstOrNull { it.courseId == selectedCourseId }?.courseName
                        ?: ""

                uiState.value.townId?.let {
                    savePlaceToCourse(
                        townId = it,
                        courseId = uiState.value.isAddMyCourseSelected,
                        placeId = uiState.value.placeDetailInfo.placeId,
                        selectedCourseName = selectedCourseName
                    )
                }
                reduce {
                    copy(isAddMyCourseSelected = null)
                }
            }

            is MapsIntent.PlaceBookMarkClick -> {
                val currentState = uiState.value.placeDetailInfo
                val isBookmarked = !currentState.isBookmarked
                val placeId = currentState.placeId

                reduce {
                    copy(placeDetailInfo = currentState.copy(isBookmarked = isBookmarked))
                }

                togglePlaceBookmark(
                    placeId = placeId,
                    isBookmarked = isBookmarked,
                    onSuccess = {
                        if (isBookmarked) {
                            postSideEffect(MapsSideEffect.ShowSuccessSavePlaceSnackBar)
                        }
                    }
                )
            }

            // Add Course
            MapsIntent.SaveCourse -> {
                val isBookmarked = !uiState.value.courseDetailInfo.isBookmarked
                reduce {
                    copy(courseDetailInfo = courseDetailInfo.copy(isBookmarked = isBookmarked))
                }

                toggleCourseBookmark(
                    courseId = uiState.value.courseDetailInfo.courseId,
                    isBookmarked = isBookmarked,
                    onSuccess = {
                        if (isBookmarked) {
                            postSideEffect(
                                MapsSideEffect.ShowSuccessSaveSingleCourseSnackBar(
                                    selectedCourseName = uiState.value.courseDetailInfo.courseName,
                                    courseId = uiState.value.courseDetailInfo.courseId
                                )
                            )
                        }
                    }
                )
            }

            is MapsIntent.SingleCoursePlaceBookMarkClick -> {
                val updatedPlaces = uiState.value.courseDetailInfo.places.map { place ->
                    if (place.placeId == intent.placeId) {
                        togglePlaceBookmark(
                            placeId = place.placeId,
                            isBookmarked = !place.isBookmarked,
                            onSuccess = {
                                if (!place.isBookmarked) {
                                    postSideEffect(
                                        MapsSideEffect.MoveCameraToPlace(
                                            latitude = place.latitude,
                                            longitude = place.longitude
                                        )
                                    )
                                    postSideEffect(MapsSideEffect.ShowSuccessSavePlaceSnackBar)
                                }
                            }
                        )
                        place.copy(isBookmarked = !place.isBookmarked)
                    } else {
                        place
                    }
                }
                reduce {
                    copy(courseDetailInfo = courseDetailInfo.copy(places = updatedPlaces))
                }
            }

            is MapsIntent.PlaceInfoClick -> {
                reduce {
                    copy(
                        selectedPlaceInfoId = if (selectedPlaceInfoId == intent.placeId) {
                            null
                        } else {
                            intent.placeId
                        }
                    )
                }
            }
            // Edit Course
            is MapsIntent.StartCourseMove -> reduce { copy(removeIconVisibility = intent.iconVisibility) }

            is MapsIntent.MoveCourseItem -> moveCourseItem(
                fromIndex = intent.fromIndex,
                toIndex = intent.toIndex
            )

            is MapsIntent.RemoveCourseItem -> {
                removeCourseItem(
                    itemToRemove = intent.itemToRemove
                )
            }

            MapsIntent.StartEditCourseIconClick -> {
                if (uiState.value.startEditCourse) {
                    reduce {
                        copy(
                            courseSaveDialogVisibility = true
                        )
                    }
                } else {
                    reduce {
                        copy(
                            startEditCourse = true,
                            coursesBeforeEdit = courseDetailInfo.places.toImmutableList(),
                            selectedPlaceInfoId = null
                        )
                    }
                }
            }

            MapsIntent.ChangeCourseSaveDialogInVisibility -> reduce {
                copy(courseSaveDialogVisibility = false)
            }

            is MapsIntent.CourseSaveDialogClick -> {
                val courseInfo = uiState.value.courseDetailInfo
                if (intent.courseSaveType == CourseSaveType.SaveToExistingCourse) {
                    saveCurrentCourse(
                        courseId = courseInfo.courseId,
                        courseSaveEntity = CourseSaveEntity(
                            name = courseInfo.courseName,
                            description = courseInfo.introduction,
                            places = courseInfo.places.mapIndexed { index, it ->
                                CoursePlace(
                                    placeId = it.placeId,
                                    order = index + 1
                                )
                            }
                        )
                    )
                } else {
                    saveNewCourse(
                        courseSaveEntity = CourseSaveEntity(
                            name = courseInfo.courseName.substringBefore("(").trim(),
                            description = courseInfo.introduction,
                            places = courseInfo.places.mapIndexed { index, it ->
                                CoursePlace(
                                    placeId = it.placeId,
                                    order = index + 1
                                )
                            }
                        )
                    )
                    postSideEffect(MapsSideEffect.ShowSuccessSaveNewCourseSnackBar)
                }
                reduce {
                    copy(
                        startEditCourse = false
                    )
                }
                sendIntent(MapsIntent.ChangeCourseSaveDialogInVisibility)
            }

            MapsIntent.BeforeEditCourseBackHandler -> {
                if (uiState.value.coursesBeforeEdit == uiState.value.courseDetailInfo.places) {
                    reduce { copy(startEditCourse = false) }
                } else {
                    reduce { copy(exitEditCourseDialogVisibility = true) }
                }
            }

            MapsIntent.BeforeEditCourseTopBarBackHandler -> {
                if (uiState.value.coursesBeforeEdit == uiState.value.courseDetailInfo.places) {
                    postSideEffect(MapsSideEffect.NavigateToBack)
                } else {
                    reduce { copy(navigateToBackDialogVisibility = true) }
                }
            }

            MapsIntent.NavigateToBackDialogInVisible -> {
                reduce { copy(navigateToBackDialogVisibility = false) }
            }

            MapsIntent.BeforeEditCourseDialogInVisible -> reduce {
                copy(exitEditCourseDialogVisibility = false)
            }

            MapsIntent.BeforeEditCourseDialogClick -> reduce {
                copy(
                    startEditCourse = false,
                    courseDetailInfo = courseDetailInfo.copy(
                        places = uiState.value.coursesBeforeEdit
                    ),
                    coursesBeforeEdit = persistentListOf(),
                    exitEditCourseDialogVisibility = false
                )
            }

            // Shared
            is MapsIntent.EmptyCourseClick -> postSideEffect(MapsSideEffect.NavigateToCourse)
            is MapsIntent.ShowMaxSizeCourseSnackBar -> postSideEffect(MapsSideEffect.ShowMaxSizeCourseSnackBar)
            is MapsIntent.ShowDuplicateSnackBar -> postSideEffect(MapsSideEffect.ShowDuplicateSnackBar)
            is MapsIntent.ReturnToHomeClick -> {
                postSideEffect(MapsSideEffect.NavigateToReturnHome)
            }

            is MapsIntent.BackButtonClick -> {
                postSideEffect(MapsSideEffect.NavigateToBack)
            }

            is MapsIntent.PlaceDetailClick -> postSideEffect(
                MapsSideEffect.NavigateToPlaceDetail(
                    intent.placeId
                )
            )
        }
    }

    private fun savePlaceToCourse(
        townId: Long,
        courseId: Long?,
        placeId: Long,
        selectedCourseName: String
    ) {
        viewModelScope.launch {
            mapsRepository.postPlaceToCourse(
                courseId = courseId!!,
                placeId = placeId
            ).onSuccess {
                getAllCourseInfo(townId = townId, placeId = placeId)
                postSideEffect(
                    MapsSideEffect.ShowSuccessSaveCourseSnackBar(
                        selectedCourseName = selectedCourseName,
                        courseId = courseId
                    )
                )
            }
        }
    }

    // TODO. 장소 상세 정보 조회 API
    fun getPlaceDetailInfo(placeId: Long) {
        viewModelScope.launch {
            mapsRepository.getPlaceDetail(placeId).onSuccess {
                reduce {
                    copy(
                        placeDetailInfo = it
                    )
                }
            }
        }
    }

    // TODO. 장소를 저장 할 코스 리스트 정보 조회 API
    fun getAllCourseInfo(
        townId: Long,
        placeId: Long
    ) {
        viewModelScope.launch {
            mapsRepository.getAddMyCourse(
                townId = townId,
                candidatePlaceId = placeId
            ).onSuccess {
                reduce {
                    copy(
                        townId = townId,
                        courses = it.toPersistentList()
                    )
                }
            }
        }
    }

    // TODO. 코스 상세 정보 조회 API
    fun getCourseDetailInfo(townId: Long, courseId: Long) {
        viewModelScope.launch {
            mapsRepository.getCourseDetail(courseId = courseId).onSuccess {
                reduce {
                    copy(
                        townId = townId,
                        courseDetailInfo = it
                    )
                }
            }
        }
    }

    private fun togglePlaceBookmark(
        placeId: Long,
        isBookmarked: Boolean,
        onSuccess: () -> Unit
    ) {
        viewModelScope.launch {
            if (isBookmarked) {
                mapsRepository.savePlaceBookMark(placeId)
            } else {
                mapsRepository.deletePlaceBookMark(placeId)
            }
            onSuccess()
        }
    }

    private fun toggleCourseBookmark(
        courseId: Long,
        isBookmarked: Boolean,
        onSuccess: () -> Unit
    ) {
        viewModelScope.launch {
            if (isBookmarked) {
                mapsRepository.postCourseBookMark(courseId = courseId)
            } else {
                mapsRepository.deleteCourseBookMark(courseId = courseId)
            }
            onSuccess()
        }
    }

    private fun saveCurrentCourse(
        courseId: Long,
        courseSaveEntity: CourseSaveEntity
    ) {
        viewModelScope.launch {
            mapsRepository.putEditCourse(
                courseId = courseId,
                courseSaveEntity = courseSaveEntity
            )
        }
    }

    private fun saveNewCourse(
        courseSaveEntity: CourseSaveEntity
    ) {
        viewModelScope.launch {
            mapsRepository.postSaveNewCourse(
                courseSaveEntity = courseSaveEntity
            ).onSuccess { courseId ->
                uiState.value.townId?.let { townId -> getCourseDetailInfo(townId, courseId) }
            }
        }
    }

    private fun filterSelectedCourseCount(courseId: Long) {
        reduce {
            copy(isAddMyCourseSelected = if (isAddMyCourseSelected == courseId) null else courseId)
        }
    }

    private fun moveCourseItem(fromIndex: Int, toIndex: Int) {
        reduce {
            val newCourseList = courseDetailInfo.places.toMutableList()
            val item = newCourseList.removeAt(fromIndex)
            newCourseList.add(toIndex, item)
            copy(courseDetailInfo = courseDetailInfo.copy(places = newCourseList.toPersistentList()))
        }
    }

    private fun removeCourseItem(itemToRemove: Int) {
        val currentList = uiState.value.courseDetailInfo.places
        if (currentList.size <= 2) {
            postSideEffect(MapsSideEffect.DisabledRemoveCourse)
            return
        }
        reduce {
            val newList = courseDetailInfo.places.toMutableList()
            newList.removeAt(itemToRemove)
            copy(
                courseDetailInfo = courseDetailInfo.copy(places = newList.toPersistentList())
            )
        }
    }
}
