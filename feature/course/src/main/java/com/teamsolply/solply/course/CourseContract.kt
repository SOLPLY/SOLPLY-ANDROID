package com.teamsolply.solply.course

import com.teamsolply.solply.course.model.CourseData
import com.teamsolply.solply.course.model.CourseUser
import com.teamsolply.solply.ui.base.SideEffect
import com.teamsolply.solply.ui.base.UiIntent
import com.teamsolply.solply.ui.base.UiState

data class CourseState(
    val user: CourseUser = CourseUser(
        userId = 0,
        nickname = "숭이숭이숭이",
        favoriteTowns = "연희동",
        persona = "HEALING",
    ),

    val courseList: List<CourseData> = listOf(
        CourseData(
            courseId = 0L,
            title = "오감으로 수집하는 하루",
            thumbnailImage = "com.teamsolply.solply.designsystem.R.drawable.img_course_dummy",
            mainTags = listOf("CAFE", "SHOPPING"),
            isBookmarked = true
        ),
        CourseData(
            courseId = 1L,
            title = "오감으로 수집하는 하루",
            thumbnailImage = "com.teamsolply.solply.designsystem.R.drawable.img_course_dummy",
            mainTags = listOf("CAFE", "SHOPPING"),
            isBookmarked = true
        ),
        CourseData(
            courseId = 2L,
            title = "오감으로 수집하는 하루",
            thumbnailImage = "com.teamsolply.solply.designsystem.R.drawable.img_course_dummy",
            mainTags = listOf("CAFE", "SHOPPING"),
            isBookmarked = true
        ),
        CourseData(
            courseId = 3L,
            title = "오감으로 수집하는 하루",
            thumbnailImage = "com.teamsolply.solply.designsystem.R.drawable.img_course_dummy",
            mainTags = listOf("CAFE", "SHOPPING"),
            isBookmarked = true
        ),
        CourseData(
            courseId = 4L,
            title = "오감으로 수집하는 하루",
            thumbnailImage = "com.teamsolply.solply.designsystem.R.drawable.img_course_dummy",
            mainTags = listOf("CAFE", "SHOPPING"),
            isBookmarked = true
        ),
        CourseData(
            courseId = 5L,
            title = "오감으로 수집하는 하루",
            thumbnailImage = "com.teamsolply.solply.designsystem.R.drawable.img_course_dummy",
            mainTags = listOf("CAFE", "SHOPPING"),
            isBookmarked = true
        ),
        CourseData(
            courseId = 6L,
            title = "오감으로 수집하는 하루",
            thumbnailImage = "com.teamsolply.solply.designsystem.R.drawable.img_course_dummy",
            mainTags = listOf("CAFE", "SHOPPING"),
            isBookmarked = true
        )
    ),
    val errorMessage: String? = null
) : UiState

sealed interface CourseIntent : UiIntent{

    data class LoadSuccess(
        val user: CourseUser,
        val courses: List<CourseData>
    ) : CourseIntent

    data class LoadFailed(
        val message: String
    ) : CourseIntent

}

sealed interface CourseSideEffect : SideEffect
