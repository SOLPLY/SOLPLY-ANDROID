package com.teamsolply.solply.oauth

import com.teamsolply.solply.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class OauthViewModel @Inject constructor() :
    BaseViewModel<OauthState, OauthIntent, OauthSideEffect>(OauthState()) {
    override fun handleIntent(intent: OauthIntent) {
        when (intent) {
            OauthIntent.KakaoLoginClick -> postSideEffect(OauthSideEffect.StartKakaoLogin)
        }
    }
}