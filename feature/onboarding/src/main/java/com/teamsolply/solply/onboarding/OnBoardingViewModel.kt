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
            is OnBoardingIntent.OnPageChanged -> {
                reduce { copy(currentPage = intent.newPage) }
            }

            is OnBoardingIntent.OnTownSelected -> {
                reduce { copy(selectedTownId = intent.townId) }
            }

            is OnBoardingIntent.OnPersonaSelected -> {
                reduce { copy(selectedPersona = intent.persona) }
            }

            is OnBoardingIntent.OnBoardingButtonClick -> {
                postSideEffect(OnBoardingSideEffect.NavigateToPlace)
            }

            is OnBoardingIntent.OnTownChanged -> {
            }

            is OnBoardingIntent.OnPersonaChanged -> {
            }

            is OnBoardingIntent.ShowStartingScreen -> {
                reduce { copy(showStartingScreen = true) }
            }
            is OnBoardingIntent.Nickname -> {
                reduce { copy(userNickname = intent.nickname) }
            }
        }
    }
}
