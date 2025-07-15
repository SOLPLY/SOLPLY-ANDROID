package com.teamsolply.solply.onboarding

import androidx.lifecycle.viewModelScope
import com.teamsolply.solply.onboarding.repository.OnBoardingRepository
import com.teamsolply.solply.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.launch
import javax.inject.Inject

@OptIn(FlowPreview::class)
@HiltViewModel
class OnBoardingViewModel @Inject constructor(
    private val onBoardingRepository: OnBoardingRepository
) :
    BaseViewModel<OnBoardingState, OnBoardingIntent, OnBoardingSideEffect>(
        OnBoardingState()
    ) {
    private val nicknameFlow = MutableStateFlow("")

    init {
        getPersonaQuestions()
        viewModelScope.launch {
            nicknameFlow
                .debounce(500)
                .filter { it.isNotBlank() }
                .collect { nickname ->
                    onBoardingRepository.checkNicknameDuplicate(nickname)
                        .onSuccess { isDuplicate ->
                            reduce { copy(isNicknameDuplicate = isDuplicate) }
                        }.onFailure {
                            reduce { copy(isNicknameDuplicate = false) }
                        }
                }
        }
    }

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

            is OnBoardingIntent.ChangeInputNickname -> {
                reduce { copy(userNickname = intent.nickname) }
                onNicknameChanged(intent.nickname)
            }

            is OnBoardingIntent.ChangeOnBoardingSuccess -> {
                reduce { copy(isOnBoardingSuccess = intent.state) }
            }

            is OnBoardingIntent.ShowStartingScreen -> {
                patchUserInfo()
                reduce { copy(showStartingScreen = true) }
            }
        }
    }

    private fun getPersonaQuestions() {
        viewModelScope.launch {
            onBoardingRepository.getPersonaQuestions().onSuccess {
                reduce {
                    copy(
                        personaList = it
                    )
                }
            }
        }
    }

    private fun onNicknameChanged(nickname: String) {
        nicknameFlow.value = nickname
    }

    private fun patchUserInfo() {
        viewModelScope.launch {
            uiState.value.selectedTownId?.let { selectedTownId ->
                uiState.value.selectedPersona?.let { selectedPersona ->
                    onBoardingRepository.patchUserInfo(
                        favoriteTown = selectedTownId,
                        persona = selectedPersona,
                        nickname = uiState.value.userNickname
                    )
                }
            }
        }
    }
}
