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
) : UiState{
    val recommendText: String
        get() = when (user.persona) {
            "HEALING" -> "차분함을 좋아하는\n${user.nickname}님을 위한 오늘의 코스"
            "EXPLORER" -> "골목 곳곳을 탐색하는\n${user.nickname}님을 위한 오늘의 코스"
            "MOODING" -> "취향을 모으는\n${user.nickname}님을 위한 오늘의 코스"
            "NATURAL" -> "힐링이 필요한\n${user.nickname}님을 위한 오늘의 코스"
            else -> "솔플리가 추천하는\n${user.nickname}님을 위한 오늘의 코스"
        }
}

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
