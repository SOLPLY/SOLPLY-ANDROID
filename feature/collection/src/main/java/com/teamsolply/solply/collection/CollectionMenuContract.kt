package com.teamsolply.solply.collection

import com.teamsolply.solply.collection.model.CourseTownEntity
import com.teamsolply.solply.collection.model.MypageTab
import com.teamsolply.solply.collection.model.PlaceTownEntity
import com.teamsolply.solply.ui.base.SideEffect
import com.teamsolply.solply.ui.base.UiIntent
import com.teamsolply.solply.ui.base.UiState

data class CollectionMenuState(
    val placeTowns: List<PlaceTownEntity> = emptyList(),
    val courseTowns: List<CourseTownEntity> = emptyList(),
    val town: String = "연희동",
    val selectedTab: MypageTab = MypageTab.PLACE,
    val isPlaceTownSelected: Boolean = false,
    val isCourseTownSelected: Boolean = false
) : UiState

sealed interface CollectionMenuIntent : UiIntent {
    // TODO 탭 이중관리
    data object SelectPlaceTab : CollectionMenuIntent
    data object SelectCourseTab : CollectionMenuIntent

    //
    data class SelectPlaceTown(val townId: Long, val townName: String) : CollectionMenuIntent
    data class SelectCourseTown(val townId: Long, val townName: String) : CollectionMenuIntent

    data class EmptyButtonClick(val mypageTab: MypageTab) : CollectionMenuIntent

    // Navigate
    data object BackButtonClick : CollectionMenuIntent
}

sealed interface CollectionSideEffect : SideEffect {
    data object NavigateToBack : CollectionSideEffect
    data object NavigateToPLace : CollectionSideEffect
    data object NavigateToCourse : CollectionSideEffect
    data class NavigateToPlaceCollection(val townId: Long, val townName: String) : CollectionSideEffect
    data class NavigateToCourseCollection(val townId: Long, val townName: String) : CollectionSideEffect
}
