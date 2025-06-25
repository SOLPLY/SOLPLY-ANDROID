package com.teamsolply.solply.onboarding

import com.teamsolply.solply.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class OnBoardingViewModel @Inject constructor() :
    BaseViewModel<OnBoardingState, OnBoardingIntent, OnBoardingSideEffect>(
        OnBoardingState()
    ) {
    override fun handleIntent(intent: OnBoardingIntent) {
        when (intent) {
            OnBoardingIntent.OnBoardingButtonClick -> postSideEffect(OnBoardingSideEffect.NavigateToPlace)
        }
    }
}
