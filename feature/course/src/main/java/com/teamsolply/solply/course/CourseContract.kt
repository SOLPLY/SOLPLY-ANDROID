package com.teamsolply.solply.course

import com.teamsolply.solply.course.model.CourseData
import com.teamsolply.solply.course.model.CourseEntity
import com.teamsolply.solply.course.model.CourseUser
import com.teamsolply.solply.course.model.TownEntity
import com.teamsolply.solply.course.model.UserEntity
import com.teamsolply.solply.ui.base.SideEffect
import com.teamsolply.solply.ui.base.UiIntent
import com.teamsolply.solply.ui.base.UiState
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf

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

    val courseList: PersistentList<CourseEntity> = persistentListOf(),
    val errorMessage: String? = null,
    // search
    val isSearchDialogVisible: Boolean = false
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

    data class CourseCardClick(val courseId: Long) : CourseIntent

    data class LoadSuccess(
        val user: CourseUser,
        val courses: List<CourseData>
    ) : CourseIntent

    data class LoadFailed(
        val message: String
    ) : CourseIntent

    // search
    data class ChangeSearchDialogVisibility(
        val visible: Boolean
    ) : CourseIntent

    data class PlaceClicked(
        val placeId: Long,
        val townId: Long
    ) : CourseIntent
}

sealed interface CourseSideEffect : SideEffect {
    data class NavigateToCourseMap(val courseId: Long) : CourseSideEffect
    data class NavigateToPlaceDetail(
        val placeId: Long,
        val townId: Long
    ) : CourseSideEffect
}
