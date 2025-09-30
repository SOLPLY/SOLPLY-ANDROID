package com.teamsolply.solply.mypage

import com.teamsolply.solply.mypage.model.PlaceInfoEntity
import com.teamsolply.solply.mypage.model.SelectedTownInfo
import com.teamsolply.solply.mypage.model.UserInfo
import com.teamsolply.solply.ui.base.SideEffect
import com.teamsolply.solply.ui.base.UiIntent
import com.teamsolply.solply.ui.base.UiState

data class MypageState(
    val userInfo: UserInfo = UserInfo(
        userId = 1,
        nickname = "숭이숭이숭이",
        selectedTown = SelectedTownInfo(0, "망원동"),
        persona = "REST"
    ),
    val nickname: String = "",
    val placeList: List<PlaceInfoEntity> = emptyList(),
) : UiState

sealed interface MypageIntent : UiIntent {
    data object Init : MypageIntent
}

sealed interface MypageSideEffect : SideEffect {
    data object NavigateToBack : MypageSideEffect
    data object NavigateToProfile : MypageSideEffect
    data object NavigateToMypage : MypageSideEffect
}
