package com.teamsolply.solply.mypage

import com.teamsolply.solply.ui.base.SideEffect
import com.teamsolply.solply.ui.base.UiIntent
import com.teamsolply.solply.ui.base.UiState

data class MypageState(
    val town: String = "연희동"
) : UiState

sealed interface MypageIntent : UiIntent

sealed interface MypageSideEffect : SideEffect
