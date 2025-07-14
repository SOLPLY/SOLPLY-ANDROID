package com.teamsolply.solply.mypage.collection.course

import com.teamsolply.solply.ui.base.SideEffect
import com.teamsolply.solply.ui.base.UiIntent
import com.teamsolply.solply.ui.base.UiState

data class CourseCollectionState(
    val selectMode: Boolean = false,
    val town: String = "연희동",
    val selectedPlaces: Set<Int> = emptySet(),
    val dialogState: Boolean = false
) : UiState

sealed interface CourseCollectionIntent : UiIntent {

    // Navigate
    data object BackButtonClick : CourseCollectionIntent
}

sealed interface CourseCollectionSideEffect : SideEffect {

    data object NavigateToBack : CourseCollectionSideEffect
//    data object NavigateToMap : CourseCollectionSideEffect
}
