package com.teamsolply.solply.maps

import androidx.lifecycle.viewModelScope
import com.teamsolply.solply.maps.repository.MapsRepository
import com.teamsolply.solply.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MapsViewModel @Inject constructor(
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

            is MapsIntent.SaveMyCourse -> {
                val selectedCourseId = currentState.addMyCourseSelectedCount.firstOrNull()
                val selectedCourseName =
                    currentState.courses.firstOrNull { it.courseId == selectedCourseId }?.title
                        ?: ""

                reduce {
                    copy(addMyCourseSelectedCount = emptyList())
                }
                postSideEffect(MapsSideEffect.ShowSuccessSaveCourseSnackBar(selectedCourseName = selectedCourseName))
                // TODO 코스에 저장 api
            }

            is MapsIntent.PlaceBookMarkClick -> {
                val bookmark = !uiState.value.placeDetailEntity.isBookmarked
                // TODO 장소 개별 저장 상태 post
                reduce {
                    copy(placeDetailEntity = placeDetailEntity.copy(isBookmarked = bookmark))
                }

                if (bookmark) {
                    postSideEffect(MapsSideEffect.ShowSuccessSavePlaceSnackBar)
                }
            }
            // Edit Course
            is MapsIntent.StartCourseMove -> reduce { copy(iconVisibility = intent.iconVisibility) }

            is MapsIntent.MoveCourseItem -> moveCourseItem(
                fromIndex = intent.fromIndex,
                toIndex = intent.toIndex
            )

            is MapsIntent.RemoveCourseItem -> {
                removeCourseItem(
                    itemToRemove = intent.itemToRemove
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
        }
    }

    //TODO. 장소 상세 정보 조회 API
    fun getPlaceDetailInfo(placeId: Int) {
        viewModelScope.launch {
            mapsRepository.getPlaceInfo(placeId).onSuccess {
                reduce {
                    copy(
                        placeDetailEntity = it
                    )
                }
            }
        }
    }

    //TODO. 장소를 저장 할 코스 리스트 정보 조회 API
    fun getAllCourseInfo() {
        viewModelScope.launch {
            mapsRepository.getAllCourses().onSuccess {
                reduce {
                    copy(
                        courses = it
                    )
                }
            }
        }
    }

    //TODO. 코스 상세 정보 조회 API
    fun getCourseDetailInfo() {
        viewModelScope.launch {
            mapsRepository.getCourseInfo(courseId = 1).onSuccess {
                reduce {
                    copy(
                        courseDetailInfo = it
                    )
                }
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

            copy(addMyCourseSelectedCount = updatedList)
        }
    }

    private fun moveCourseItem(fromIndex: Int, toIndex: Int) {
        reduce {
            val newCourseList = course.toMutableList()
            val item = newCourseList.removeAt(fromIndex)
            newCourseList.add(toIndex, item)
            copy(course = newCourseList)
        }
    }

    private fun removeCourseItem(itemToRemove: Int) {
        val currentList = uiState.value.course
        if (currentList.size <= 2) {
            postSideEffect(MapsSideEffect.DisabledRemoveCourse)
            return
        }
        reduce {
            val newList = course.toMutableList()
            newList.removeAt(itemToRemove)
            copy(
                course = newList
            )
        }
    }
}
