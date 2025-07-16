package com.teamsolply.solply.mypage.collection.course

import com.teamsolply.solply.mypage.model.CourseInfoEntity
import com.teamsolply.solply.ui.base.SideEffect
import com.teamsolply.solply.ui.base.UiIntent
import com.teamsolply.solply.ui.base.UiState

data class CourseCollectionState(
    val selectMode: Boolean = false,
    val townId: Int = 1,
    val townName: String = "",
    val courses: List<CourseInfoEntity> = emptyList(),
    val selectedCourses: Set<Int> = emptySet(),
    val dialogState: Boolean = false
) : UiState

sealed interface CourseCollectionIntent : UiIntent {

    data class Init(val townId: Int, val townName: String) : CourseCollectionIntent

    data object SelectButtonClick : CourseCollectionIntent
    data object DeleteButtonClick : CourseCollectionIntent
    data object CancelButtonClick : CourseCollectionIntent

    data object DialogConfirmClick : CourseCollectionIntent
    data object DialogDismissClick : CourseCollectionIntent

    data class CourseCardClick(val courseId: Int, val index: Int) : CourseCollectionIntent

    // Navigate
    data object BackButtonClick : CourseCollectionIntent
}

sealed interface CourseCollectionSideEffect : SideEffect {

    data object NavigateToBack : CourseCollectionSideEffect
    data object NavigateToMap : CourseCollectionSideEffect
}
