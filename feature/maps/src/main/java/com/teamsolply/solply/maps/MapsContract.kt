package com.teamsolply.solply.maps

import com.teamsolply.solply.maps.model.CourseDetailEntity
import com.teamsolply.solply.maps.model.CourseInfoEntity
import com.teamsolply.solply.maps.model.CourseSaveType
import com.teamsolply.solply.maps.model.Place
import com.teamsolply.solply.maps.model.PlaceDetailEntity
import com.teamsolply.solply.model.PlaceType
import com.teamsolply.solply.ui.base.SideEffect
import com.teamsolply.solply.ui.base.UiIntent
import com.teamsolply.solply.ui.base.UiState
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf

internal data class MapsState(
    // Add Place
    val placeDetailInfo: PlaceDetailEntity = defaultPlaceDetailEntity,
    val courses: PersistentList<CourseInfoEntity> = persistentListOf(),
    val startAddMyCourse: Boolean = false,
    val addMyCourseSelectedCount: PersistentList<Int> = persistentListOf(),
    // Add Course
    val courseDetailInfo: CourseDetailEntity = courseDetailEntity,
    val selectedPlaceInfoId: Int? = null,
    // Edit Course
    val removeIconVisibility: Boolean = false,
    val startEditCourse: Boolean = false,
    val courseSaveDialogVisibility: Boolean = false,
    val coursesBeforeEdit: ImmutableList<Place> = persistentListOf(),
    val exitEditCourseDialogVisibility: Boolean = false
) : UiState

internal sealed interface MapsIntent : UiIntent {
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
    data object SaveCourse : MapsIntent
    data class SingleCoursePlaceBookMarkClick(
        val placeId: Int
    ) : MapsIntent

    data class PlaceInfoClick(
        val placeId: Int
    ) : MapsIntent

    // Edit Course
    data object StartEditCourseIconClick : MapsIntent

    data object ChangeCourseSaveDialogInVisibility : MapsIntent

    data class CourseSaveDialogClick(
        val courseSaveType: CourseSaveType
    ) : MapsIntent

    data object BeforeEditCourseBackHandler : MapsIntent

    data object BeforeEditCourseDialogInVisible : MapsIntent

    data object BeforeEditCourseDialogClick : MapsIntent

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

internal sealed interface MapsSideEffect : SideEffect {
    // Add Place
    data object ShowMaxSizeCourseSnackBar : MapsSideEffect
    data class ShowSuccessSaveCourseSnackBar(val selectedCourseName: String) : MapsSideEffect
    data object ShowSuccessSavePlaceSnackBar : MapsSideEffect

    // Edit Course
    data object DisabledRemoveCourse : MapsSideEffect
    data object ShowSuccessSaveSingleCourseSnackBar : MapsSideEffect
    data object ShowSuccessSaveNewCourseSnackBar : MapsSideEffect

    // Shared
    data object NavigateToCourse : MapsSideEffect
    data object NavigateToReturnHome : MapsSideEffect
    data object NavigateToBack : MapsSideEffect
}

internal val defaultPlaceDetailEntity = PlaceDetailEntity(
    placeId = 0,
    placeName = "",
    primaryTag = PlaceType.EMPTY,
    address = "",
    latitude = 37.4979,
    longitude = 127.0276,
    description = "",
    imageInfos = persistentListOf(),
    contactNumber = "",
    openingHours = "",
    snsLink = persistentListOf(),
    isBookmarked = false,
    placeType = "",
    placeDefaultId = 0
)

internal val courseDetailEntity = CourseDetailEntity(
    courseId = 0,
    courseName = "",
    introduction = "",
    isBookmarked = false,
    places = persistentListOf()
)
