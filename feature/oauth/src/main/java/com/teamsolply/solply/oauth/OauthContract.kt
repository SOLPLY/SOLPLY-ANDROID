package com.teamsolply.solply.oauth

import com.teamsolply.solply.ui.base.SideEffect
import com.teamsolply.solply.ui.base.UiIntent
import com.teamsolply.solply.ui.base.UiState

data class OauthState(
    val g: String = ""
) : UiState

sealed interface OauthIntent : UiIntent {
    data object KakaoLoginClick : OauthIntent
    data class KakaoLoginSuccess(val provider: String, val accessToken: String) : OauthIntent
    data class KakaoLoginFailure(val error: Throwable) : OauthIntent
    data class SaveJwtToken(val accessToken: String, val refreshToken: String) : OauthIntent
}

sealed interface OauthSideEffect : SideEffect {
    data object StartKakaoLogin : OauthSideEffect
    data object NavigateToOnBoarding : OauthSideEffect
}
