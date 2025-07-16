package com.teamsolply.solply.course

import com.teamsolply.solply.course.model.CourseData
import com.teamsolply.solply.course.model.CourseEntity
import com.teamsolply.solply.course.model.CourseUser
import com.teamsolply.solply.course.model.TownEntity
import com.teamsolply.solply.course.model.UserEntity
import com.teamsolply.solply.model.PlaceType
import com.teamsolply.solply.ui.base.SideEffect
import com.teamsolply.solply.ui.base.UiIntent
import com.teamsolply.solply.ui.base.UiState

data class CourseState(
    val user: UserEntity = UserEntity(
        userId = 0,
        nickname = "숭이숭이숭이",
        selectedTown = TownEntity(
            townId = 1,
            townName = "연희동"
        ),
        persona = "HEALING"
    ),

    val courseList: List<CourseEntity> = listOf(
        CourseEntity(
            courseId = 0,
            courseName = "오감으로 수집하는 하루",
            imageUrl = "com.teamsolply.solply.designsystem.R.drawable.img_course_dummy",
            tagList = listOf(PlaceType.CAFE, PlaceType.SHOPPING),
            isSaved = true
        ),
        CourseEntity(
            courseId = 1,
            courseName = "오감으로 수집하는 하루",
            imageUrl = "com.teamsolply.solply.designsystem.R.drawable.img_course_dummy",
            tagList = listOf(PlaceType.CAFE, PlaceType.SHOPPING),
            isSaved = true
        ),
        CourseEntity(
            courseId = 2,
            courseName = "오감으로 수집하는 하루",
            imageUrl = "com.teamsolply.solply.designsystem.R.drawable.img_course_dummy",
            tagList = listOf(PlaceType.CAFE, PlaceType.SHOPPING),
            isSaved = true
        ),
        CourseEntity(
            courseId = 3,
            courseName = "오감으로 수집하는 하루",
            imageUrl = "com.teamsolply.solply.designsystem.R.drawable.img_course_dummy",
            tagList = listOf(PlaceType.CAFE, PlaceType.SHOPPING),
            isSaved = true
        ),
        CourseEntity(
            courseId = 4,
            courseName = "오감으로 수집하는 하루",
            imageUrl = "com.teamsolply.solply.designsystem.R.drawable.img_course_dummy",
            tagList = listOf(PlaceType.CAFE, PlaceType.SHOPPING),
            isSaved = true
        ),
        CourseEntity(
            courseId = 5,
            courseName = "오감으로 수집하는 하루",
            imageUrl = "com.teamsolply.solply.designsystem.R.drawable.img_course_dummy",
            tagList = listOf(PlaceType.CAFE, PlaceType.SHOPPING),
            isSaved = true
        ),
        CourseEntity(
            courseId = 6,
            courseName = "오감으로 수집하는 하루",
            imageUrl = "com.teamsolply.solply.designsystem.R.drawable.img_course_dummy",
            tagList = listOf(PlaceType.CAFE, PlaceType.SHOPPING),
            isSaved = true
        )
    ),
    val errorMessage: String? = null
) : UiState {
    val recommendText: String
        get() = when (user.persona) {
            "HEALING" -> "차분함을 좋아하는\n${user.nickname}님을 위한 오늘의 코스"
            "EXPLORER" -> "골목 곳곳을 탐색하는\n${user.nickname}님을 위한 오늘의 코스"
            "MOODING" -> "취향을 모으는\n${user.nickname}님을 위한 오늘의 코스"
            "NATURAL" -> "힐링이 필요한\n${user.nickname}님을 위한 오늘의 코스"
            else -> "솔플리가 추천하는\n${user.nickname}님을 위한 오늘의 코스"
        }
}

sealed interface CourseIntent : UiIntent {
    data object Init : CourseIntent

    data class CourseCardClick(val courseId: Int) : CourseIntent

    data class LoadSuccess(
        val user: CourseUser,
        val courses: List<CourseData>
    ) : CourseIntent

    data class LoadFailed(
        val message: String
    ) : CourseIntent
}

sealed interface CourseSideEffect : SideEffect {
    data class NavigateToCourseMap(val courseId: Int) : CourseSideEffect
}
