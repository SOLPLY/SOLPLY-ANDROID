package com.teamsolply.solply.oauth

import androidx.lifecycle.viewModelScope
import com.teamsolply.solply.oauth.repository.OauthRepository
import com.teamsolply.solply.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OauthViewModel @Inject constructor(
    private val oauthRepository: OauthRepository
) :
    BaseViewModel<OauthState, OauthIntent, OauthSideEffect>(OauthState()) {
    override fun handleIntent(intent: OauthIntent) {
        when (intent) {
            OauthIntent.KakaoLoginClick -> postSideEffect(OauthSideEffect.StartKakaoLogin)
            is OauthIntent.KakaoLoginSuccess -> postSocialLogin(
                provider = intent.provider,
                oauthAccessToken = intent.accessToken
            )

            is OauthIntent.KakaoLoginFailure -> {
                TODO()
            }

            OauthIntent.GoogleLoginClick -> postSideEffect(OauthSideEffect.StartGoogleLogin)

            is OauthIntent.GoogleLoginSuccess -> postSocialLogin(
                provider = intent.provider,
                oauthAccessToken = intent.accessToken
            )

            is OauthIntent.GoogleLoginFailure -> {
                TODO()
            }

            is OauthIntent.SaveJwtToken -> {
                viewModelScope.launch {
                    oauthRepository.saveJwtToken(
                        intent.accessToken,
                        intent.refreshToken
                    ).onSuccess {
                        if (intent.isNewUser) {
                            postSideEffect(OauthSideEffect.NavigateToOnBoarding)
                        } else {
                            postSideEffect(OauthSideEffect.NavigateToPlace)
                        }
                    }
                }
            }
        }
    }

    private fun postSocialLogin(
        provider: String,
        oauthAccessToken: String
    ) {
        viewModelScope.launch {
            oauthRepository.postSocialLogin(
                provider = provider,
                oauthAccessToken = oauthAccessToken
            ).onSuccess {
                sendIntent(
                    OauthIntent.SaveJwtToken(
                        accessToken = it.accessToken,
                        refreshToken = it.refreshToken,
                        isNewUser = it.isNewUser
                    )
                )
            }
        }
    }
}
