package com.teamsolply.solply.onboarding

import androidx.lifecycle.viewModelScope
import com.teamsolply.solply.onboarding.repository.OnBoardingRepository
import com.teamsolply.solply.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OnBoardingViewModel @Inject constructor(
    private val onBoardingRepository: OnBoardingRepository
) :
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

            is OnBoardingIntent.ChangeInputNickname -> {
                viewModelScope.launch {
                    onBoardingRepository.checkNicknameDuplicate(nickname = intent.nickname)
                        .onSuccess {
                            reduce { copy(isNicknameDuplicate = it) }
                        }
                }
                reduce { copy(userNickname = intent.nickname) }
            }
        }
    }

    private fun patchUserInfo() {
        viewModelScope.launch {
            //TODO. 여기서 회원가입 api 쏘기
            //onBoardingRepository
        }
    }
}
