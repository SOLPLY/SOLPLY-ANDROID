package com.teamsolply.solply.oauth

import androidx.lifecycle.viewModelScope
import com.teamsolply.solply.oauth.model.TokenEntity
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
            is OauthIntent.KakaoLoginSuccess -> saveJwtToken(
                intent.accessToken,
                intent.refreshToken
            )

            is OauthIntent.KakaoLoginFailure -> {
                TODO()
            }
        }
    }

    private fun saveJwtToken(accessToken: String, refreshToken: String?) {
        viewModelScope.launch {
            refreshToken?.let { checkedRefreshToken ->
                val token = TokenEntity(
                    accessToken = accessToken,
                    refreshToken = checkedRefreshToken
                )
                oauthRepository.saveJwtToken(token)
                    .onSuccess {
                        postSideEffect(OauthSideEffect.NavigateToOnBoarding)
                    }
            }
        }
    }
}
