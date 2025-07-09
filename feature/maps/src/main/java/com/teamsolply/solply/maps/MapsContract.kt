package com.teamsolply.solply.maps

import com.teamsolply.solply.maps.model.CourseDetailEntity
import com.teamsolply.solply.maps.model.CourseInfoEntity
import com.teamsolply.solply.maps.model.PlaceDetailEntity
import com.teamsolply.solply.model.PlaceType
import com.teamsolply.solply.ui.base.SideEffect
import com.teamsolply.solply.ui.base.UiIntent
import com.teamsolply.solply.ui.base.UiState

data class MapsState(
    // Add Place
    val placeDetailEntity: PlaceDetailEntity = defaultPlaceDetailEntity,
    val courses: List<CourseInfoEntity> = emptyList(),
    val startAddMyCourse: Boolean = false,
    val addMyCourseSelectedCount: List<Int> = emptyList(),
    // Add Course
    val courseDetailInfo: CourseDetailEntity = courseDetailEntity,
    // Edit Course
    val course: List<PlaceDetailEntity> = emptyList(),
    val iconVisibility: Boolean = false
) : UiState

sealed interface MapsIntent : UiIntent {
    // Add Place
    data class AddPlaceClick(
        val addPlace: Boolean
    ) : MapsIntent

    data class SelectedCourseForPlace(
        val courseId: Int
    ) : MapsIntent

    data object SavePlaceInMyCourse : MapsIntent

    data object PlaceBookMarkClick : MapsIntent

    // Add course
    data object SaveCourse: MapsIntent
    // Edit Course
    // Item Drag and Remove
    data class StartCourseMove(
        val iconVisibility: Boolean
    ) : MapsIntent

    data class MoveCourseItem(
        val fromIndex: Int,
        val toIndex: Int
    ) : MapsIntent

    data class RemoveCourseItem(
        val itemToRemove: Int
    ) : MapsIntent

    // Navigate
    data object EmptyCourseClick : MapsIntent
    data object ShowMaxSizeCourseSnackBar : MapsIntent
    data object ReturnToHomeClick : MapsIntent
    data object BackButtonClick : MapsIntent
}

sealed interface MapsSideEffect : SideEffect {
    // Add Place
    data object ShowMaxSizeCourseSnackBar : MapsSideEffect
    data class ShowSuccessSaveCourseSnackBar(val selectedCourseName: String) : MapsSideEffect
    data object ShowSuccessSavePlaceSnackBar : MapsSideEffect

    // Edit Course
    data object DisabledRemoveCourse : MapsSideEffect

    // Shared
    data object NavigateToCourse : MapsSideEffect
    data object NavigateToReturnHome : MapsSideEffect
    data object NavigateToBack : MapsSideEffect
}

val defaultPlaceDetailEntity = PlaceDetailEntity(
    placeId = 0,
    placeName = "",
    primaryTag = PlaceType.EMPTY,
    address = "",
    latitude = 37.4979,
    longitude = 127.0276,
    description = "",
    imageInfos = emptyList(),
    contactNumber = "",
    openingHours = "",
    snsLink = emptyList(),
    isBookmarked = false,
    placeType = "",
    placeDefaultId = 0
)

val courseDetailEntity = CourseDetailEntity(
    courseId = 0,
    courseName = "",
    introduction = "",
    isBookmarked = false,
    places = emptyList()
)