package com.teamsolply.solply.mypage

import com.teamsolply.solply.mypage.model.CourseTownEntity
import com.teamsolply.solply.mypage.model.MypageTab
import com.teamsolply.solply.mypage.model.PlaceTownEntity
import com.teamsolply.solply.ui.base.SideEffect
import com.teamsolply.solply.ui.base.UiIntent
import com.teamsolply.solply.ui.base.UiState

data class MypageState(
    val placeTowns: List<PlaceTownEntity> = emptyList(),
    val courseTowns: List<CourseTownEntity> = emptyList(),
    val town: String = "연희동",
    val selectedTab: MypageTab = MypageTab.PLACE,
    val isPlaceTownSelected: Boolean = false,
    val isCourseTownSelected: Boolean = false
) : UiState

sealed interface MypageIntent : UiIntent {
    // TODO 탭 이중관리
    data object SelectPlaceTab : MypageIntent
    data object SelectCourseTab : MypageIntent

    //
    data class SelectPlaceTown(val townId: Int, val townName: String) : MypageIntent
    data class SelectCourseTown(val townId: Int, val townName: String) : MypageIntent

    data class EmptyButtonClick(val mypageTab: MypageTab) : MypageIntent

    // Navigate
    data object BackButtonClick : MypageIntent
}

sealed interface MypageSideEffect : SideEffect {
    data object NavigateToBack : MypageSideEffect
    data object NavigateToPLace : MypageSideEffect
    data object NavigateToCourse : MypageSideEffect
    data class NavigateToPlaceCollection(val townId: Int, val townName: String) : MypageSideEffect
    data class NavigateToCourseCollection(val townId: Int, val townName: String) : MypageSideEffect
}
