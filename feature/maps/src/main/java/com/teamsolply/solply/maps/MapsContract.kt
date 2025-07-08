package com.teamsolply.solply.maps

import com.teamsolply.solply.maps.model.PlaceInfo
import com.teamsolply.solply.model.PlaceType
import com.teamsolply.solply.ui.base.SideEffect
import com.teamsolply.solply.ui.base.UiIntent
import com.teamsolply.solply.ui.base.UiState
import okhttp3.internal.immutableListOf

data class MapsState(
    // Edit Course
    val courses: List<PlaceInfo> = immutableListOf(
        PlaceInfo(
            placeId = 0,
            placeName = "0번",
            placeTag = PlaceType.CAFE,
            placeAddress = "주소",
            placeImageRes = com.teamsolply.solply.designsystem.R.drawable.img_course_dummy,
            priority = 0,
            latitude = 37.4979,
            longitude = 127.0276
        ),
        PlaceInfo(
            placeId = 1,
            placeName = "1번",
            placeTag = PlaceType.BOOK,
            placeAddress = "주소",
            placeImageRes = com.teamsolply.solply.designsystem.R.drawable.img_course_dummy,
            priority = 1,
            latitude = 37.4999,
            longitude = 127.0286
        ),
        PlaceInfo(
            placeId = 2,
            placeName = "2번",
            placeTag = PlaceType.UNIQUE,
            placeAddress = "주소",
            placeImageRes = com.teamsolply.solply.designsystem.R.drawable.img_course_dummy,
            priority = 2,
            latitude = 37.4999,
            longitude = 127.0376
        ),
        PlaceInfo(
            placeId = 3,
            placeName = "3번",
            placeTag = PlaceType.FOOD,
            placeAddress = "주소",
            placeImageRes = com.teamsolply.solply.designsystem.R.drawable.img_course_dummy,
            priority = 3,
            latitude = 37.4991,
            longitude = 127.0255
        ),
        PlaceInfo(
            placeId = 4,
            placeName = "4번",
            placeTag = PlaceType.SHOPPING,
            placeAddress = "주소",
            placeImageRes = com.teamsolply.solply.designsystem.R.drawable.img_course_dummy,
            priority = 4,
            latitude = 37.4980,
            longitude = 127.0226
        ),
        PlaceInfo(
            placeId = 5,
            placeName = "5번",
            placeTag = PlaceType.BOOK,
            placeAddress = "주소",
            placeImageRes = com.teamsolply.solply.designsystem.R.drawable.img_course_dummy,
            priority = 5,
            latitude = 37.4999,
            longitude = 127.0226
        )
    ),
    val iconVisibility: Boolean = false
) : UiState

sealed interface MapsIntent : UiIntent {
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
    data object ReturnToHomeClick : MapsIntent
    data object BackButtonClick : MapsIntent
}

sealed interface MapsSideEffect : SideEffect {
    data object DisabledRemoveCourse : MapsSideEffect
    data object NavigateToReturnHome : MapsSideEffect
    data object NavigateToBack : MapsSideEffect
}
