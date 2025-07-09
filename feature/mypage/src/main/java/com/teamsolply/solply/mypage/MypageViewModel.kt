package com.teamsolply.solply.mypage

import com.teamsolply.solply.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MypageViewModel @Inject constructor() :
    BaseViewModel<MypageState, MypageIntent, MypageSideEffect>(MypageState()) {
    override fun handleIntent(intent: MypageIntent) {
        when (intent) {
            is MypageIntent.BackButtonClick -> {
                postSideEffect(MypageSideEffect.NavigateToBack)
            }
        }
    }
}
