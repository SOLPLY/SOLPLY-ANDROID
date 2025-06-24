package com.teamsolply.solply.oauth

import com.teamsolply.solply.ui.base.SideEffect
import com.teamsolply.solply.ui.base.UiIntent
import com.teamsolply.solply.ui.base.UiState

data class OauthState(
    val g: String = ""
) : UiState

sealed interface OauthIntent : UiIntent {
    data object KakaoLoginClick : OauthIntent
}

sealed interface OauthSideEffect : SideEffect {
    data object StartKakaoLogin : OauthSideEffect
}
