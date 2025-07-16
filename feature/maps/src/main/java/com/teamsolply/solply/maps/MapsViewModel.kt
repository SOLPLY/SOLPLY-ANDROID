package com.teamsolply.solply.maps

import android.util.Log
import androidx.lifecycle.viewModelScope
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
                val selectedCourseId = currentState.addMyCourseSelectedCount.firstOrNull()
                val selectedCourseName =
                    currentState.courses.firstOrNull { it.courseId == selectedCourseId }?.title
                        ?: ""

                reduce {
                    copy(addMyCourseSelectedCount = persistentListOf())
                }
                postSideEffect(MapsSideEffect.ShowSuccessSaveCourseSnackBar(selectedCourseName = selectedCourseName))
                // TODO 코스에 저장 api
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
                val bookmark = !uiState.value.courseDetailInfo.isBookmarked
                // TODO 코스 개별 저장 post
                reduce {
                    copy(courseDetailInfo = courseDetailInfo.copy(isBookmarked = bookmark))
                }

                if (bookmark) {
                    postSideEffect(MapsSideEffect.ShowSuccessSaveSingleCourseSnackBar)
                }
            }

            is MapsIntent.SingleCoursePlaceBookMarkClick -> {
                val updatedPlaces = uiState.value.courseDetailInfo.places.map { place ->
                    if (place.placeId == intent.placeId) {
                        place.copy(isBookmarked = !place.isBookmarked)
                    } else {
                        place
                    }
                }

                reduce {
                    copy(courseDetailInfo = courseDetailInfo.copy(places = updatedPlaces))
                }
                // TODO. 장소 개별 저장 API

                viewModelScope.launch {

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
                if (intent.courseSaveType == CourseSaveType.SaveToExistingCourse) {
                    // TODO. 지금 코스에 저장 API
                } else {
                    postSideEffect(MapsSideEffect.ShowSuccessSaveNewCourseSnackBar)
                    // TODO. 새 코스에 저장 API - 명세서 바뀌는거 보고
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

            MapsIntent.BeforeEditCourseDialogInVisible -> reduce {
                copy(exitEditCourseDialogVisibility = false)
            }

            MapsIntent.BeforeEditCourseDialogClick -> reduce {
                copy(
                    startEditCourse = false,
                    coursesBeforeEdit = persistentListOf(),
                    exitEditCourseDialogVisibility = false
                )
            }

            // Shared
            is MapsIntent.EmptyCourseClick -> postSideEffect(MapsSideEffect.NavigateToCourse)
            is MapsIntent.ShowMaxSizeCourseSnackBar -> postSideEffect(MapsSideEffect.ShowMaxSizeCourseSnackBar)
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
                placeId = placeId
            ).onSuccess {
                reduce {
                    copy(
                        courses = it.toPersistentList()
                    )
                }
            }.onFailure {
                Log.d("asdasdasd", it.toString())
            }
        }
    }

    // TODO. 코스 상세 정보 조회 API
    fun getCourseDetailInfo() {
        viewModelScope.launch {
            mapsRepository.getCourseDetail(courseId = 1).onSuccess {
                reduce {
                    copy(
                        courseDetailInfo = it
                    )
                }
            }
        }
    }

    fun togglePlaceBookmark(
        placeId: Long,
        isBookmarked: Boolean,
        onSuccess: () -> Unit
    ) {
        viewModelScope.launch {
            val result = if (isBookmarked) {
                mapsRepository.savePlaceBookMark(placeId)
            } else {
                mapsRepository.deletePlaceBookMark(placeId)
            }

            result.onSuccess {
                onSuccess()
            }
        }
    }

    private fun filterSelectedCourseCount(courseId: Int) {
        reduce {
            val updatedList = if (addMyCourseSelectedCount.contains(courseId)) {
                addMyCourseSelectedCount - courseId
            } else {
                addMyCourseSelectedCount + courseId
            }

            copy(addMyCourseSelectedCount = updatedList.toPersistentList())
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
