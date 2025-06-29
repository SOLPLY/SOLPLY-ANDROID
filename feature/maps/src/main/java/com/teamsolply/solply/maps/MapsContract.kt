package com.teamsolply.solply.maps

import com.teamsolply.solply.maps.model.CourseInfo
import com.teamsolply.solply.ui.base.SideEffect
import com.teamsolply.solply.ui.base.UiIntent
import com.teamsolply.solply.ui.base.UiState
import okhttp3.internal.immutableListOf

data class MapsState(
    val course: List<CourseInfo> = immutableListOf(
        CourseInfo(
            courseId = 0,
            courseName = "0번"
        ),
        CourseInfo(
            courseId = 1,
            courseName = "1번"
        ),
        CourseInfo(
            courseId = 2,
            courseName = "2번"
        ),
        CourseInfo(
            courseId = 3,
            courseName = "3번"
        ),
        CourseInfo(
            courseId = 4,
            courseName = "4번"
        ),
        CourseInfo(
            courseId = 5,
            courseName = "5번"
        ),
        CourseInfo(
            courseId = 6,
            courseName = "6번"
        ),
        CourseInfo(
            courseId = 7,
            courseName = "7번"
        ),CourseInfo(
            courseId = 8,
            courseName = "8번"
        ),
        CourseInfo(
            courseId = 9,
            courseName = "9번"
        ),


    ),
    val iconVisibility: Boolean = false,
) : UiState

sealed interface MapsIntent : UiIntent {
    data class StartCourseMove(
        val iconVisibility: Boolean
    ) : MapsIntent

    data class MoveCourseItem(
        val fromIndex: Int,
        val toIndex: Int
    ) : MapsIntent

    data class RemoveCourseItem(
        val itemToRemove: Int,
    ) : MapsIntent
}

sealed interface MapsSideEffect : SideEffect {

}