package com.teamsolply.solply.mypage

import com.teamsolply.solply.mypage.model.MypageTab
import com.teamsolply.solply.mypage.model.TownCard
import com.teamsolply.solply.ui.base.SideEffect
import com.teamsolply.solply.ui.base.UiIntent
import com.teamsolply.solply.ui.base.UiState
import okhttp3.internal.immutableListOf

data class MypageState(
    val towns: List<TownCard> = immutableListOf(
        TownCard(
            townName = "연희동",
            imageUrl = ""
        ),
        TownCard(
            townName = "망원동",
            imageUrl = ""
        )
    ),
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
    data class SelectPlaceTown(val selectedTown: String) : MypageIntent
    data class SelectCourseTown(val selectedTown: String) : MypageIntent

    // Navigate
    data object BackButtonClick : MypageIntent
}

sealed interface MypageSideEffect : SideEffect {
    data object NavigateToBack : MypageSideEffect
    data class NavigateToPlaceCollection(val town: String) : MypageSideEffect
    data class NavigateToCourseCollection(val town: String) : MypageSideEffect
}
