package com.teamsolply.solply.maps

import com.teamsolply.solply.maps.model.CourseInfo
import com.teamsolply.solply.ui.base.SideEffect
import com.teamsolply.solply.ui.base.UiIntent
import com.teamsolply.solply.ui.base.UiState
import okhttp3.internal.immutableListOf

data class MapsState(
    // Edit Course
    val courses: List<CourseInfo> = immutableListOf(
        CourseInfo(
            courseId = 0,
            courseName = "0번",
            priority = 0,
            latitude = 37.4979,
            longitude = 127.0276
        ),
        CourseInfo(
            courseId = 1,
            courseName = "1번",
            priority = 1,
            latitude = 37.4999,
            longitude = 127.0286
        ),
        CourseInfo(
            courseId = 2,
            courseName = "2번",
            priority = 2,
            latitude = 37.4999,
            longitude = 127.0376
        ),
        CourseInfo(
            courseId = 3,
            courseName = "3번",
            priority = 3,
            latitude = 37.4991,
            longitude = 127.0255
        ),
        CourseInfo(
            courseId = 4,
            courseName = "4번",
            priority = 4,
            latitude = 37.4980,
            longitude = 127.0226
        ),
        CourseInfo(
            courseId = 5,
            courseName = "5번",
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
