package com.teamsolply.solply.maps

import com.teamsolply.solply.maps.model.PlaceInfo
import com.teamsolply.solply.model.PlaceType
import com.teamsolply.solply.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MapsViewModel @Inject constructor() :
    BaseViewModel<MapsState, MapsIntent, MapsSideEffect>(MapsState()) {

    init {
        getPlaceDetailInfo()
    }

    override fun handleIntent(intent: MapsIntent) {
        when (intent) {
            //Add Place
            is MapsIntent.AddPlaceClick -> reduce { copy(startAddMyCourse = intent.addPlace) }

            is MapsIntent.SelectedCourseForPlace -> {
                filterSelectedCourseCount(intent.courseId)
            }

            is MapsIntent.SaveMyCourse -> {
                val selectedCourseId = currentState.addMyCourseSelectedCount.firstOrNull()
                val selectedCourseName = currentState.courses.firstOrNull { it.courseId == selectedCourseId }?.title ?: ""

                reduce {
                    copy(addMyCourseSelectedCount = emptyList())
                }
                postSideEffect(MapsSideEffect.ShowSuccessSaveCourseSnackBar(selectedCourseName = selectedCourseName))


                //TODO 코스 저장 api
            }
            //Edit Course
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
            is MapsIntent.ShowMaxSizeCourseSnackBar -> postSideEffect(MapsSideEffect.ShowMaxSizeCourseSnackBar)
            is MapsIntent.ReturnToHomeClick -> {
                postSideEffect(MapsSideEffect.NavigateToReturnHome)
            }

            is MapsIntent.BackButtonClick -> {
                postSideEffect(MapsSideEffect.NavigateToBack)
            }
        }
    }

    fun getPlaceDetailInfo() {
        reduce {
            copy(
                placeInfo = PlaceInfo(
                    placeId = 1,
                    placeName = "유어마인드",
                    primaryTag = PlaceType.CAFE,
                    address = "서울 서대문구 연희로 189 - 16 단독주택 어쩌구",
                    priority = 0,
                    latitude = 37.4979,
                    longitude = 127.0276,
                    description = "귀여운 당고 디저트와 커피, 에이드가 있는 펫 프렌들리",
                    imageUrls = listOf(
                        com.teamsolply.solply.designsystem.R.drawable.img_place_img_dummy,
                        com.teamsolply.solply.designsystem.R.drawable.img_place_img_dummy,
                        com.teamsolply.solply.designsystem.R.drawable.img_place_img_dummy,
                    ),
                    contactNumber = "0507 - 1324 - 9018",
                    openingHours = "월 - 금 10:00 - 19:00",
                    snsLink = "내용",
                    isBookmarked = true
                )
            )
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

    private fun removeAddPlaceState() {
        reduce {
            copy(
                addMyCourseSelectedCount = emptyList()
            )
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
