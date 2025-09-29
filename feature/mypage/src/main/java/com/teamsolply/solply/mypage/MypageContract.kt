package com.teamsolply.solply.mypage

import com.teamsolply.solply.ui.base.SideEffect
import com.teamsolply.solply.ui.base.UiIntent
import com.teamsolply.solply.ui.base.UiState

data class MypageState(
    val town: String = "연희동",
    val nickname: String = ""
) : UiState

sealed interface MypageIntent : UiIntent

sealed interface MypageSideEffect : SideEffect {
    data object NavigateToBack : MypageSideEffect
    data object NavigateToProfile : MypageSideEffect
    data object NavigateToMypage : MypageSideEffect
}
