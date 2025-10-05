package com.teamsolply.solply.mypage.profile

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.teamsolply.solply.mypage.repository.MypageRepository
import com.teamsolply.solply.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileEditViewModel @Inject constructor(
    private val mypageRepository: MypageRepository
) :
    BaseViewModel<ProfileEditState, ProfileEditIntent, ProfileEditSideEffect>(ProfileEditState()) {
    private val nicknameFlow = MutableStateFlow("")

    override fun handleIntent(intent: ProfileEditIntent) {
        when (intent) {
            ProfileEditIntent.Init -> init()

            is ProfileEditIntent.ChangeInputNickname -> {
                reduce {
                    copy(inputNickname = intent.nickname)
                }
            }

            ProfileEditIntent.ValidateNickname -> {

            }

            ProfileEditIntent.BackButtonClick -> {
                reduce {
                    copy(dialogState = true)
                }
            }

            is ProfileEditIntent.ChangeEditingSuccess -> {
                reduce {
                    copy(
                        isEditSuccess = intent.state
                    )
                }
            }

            ProfileEditIntent.DropDownIconClick -> {
                reduce {
                    copy(
                        isDropped = !isDropped
                    )
                }
            }

            is ProfileEditIntent.DropDownItemClick -> {
                reduce {
                    copy(
                        selectedPersonaIndex = intent.index,
                        isDropped = !isDropped
                    )
                }
            }

            ProfileEditIntent.CompleteButtonClick -> {
                viewModelScope.launch {
                    // TODO 프로필 수정 patch api
                    postSideEffect(
                        ProfileEditSideEffect.NavigateToMypage
                    )
                }
            }

            ProfileEditIntent.DialogConfirmClick -> {
                viewModelScope.launch {
                    // TODO 로그아웃 api
                    postSideEffect(
                        ProfileEditSideEffect.NavigateToBack
                    )
                }
            }

            ProfileEditIntent.DialogDismissClick -> {
                reduce {
                    copy(dialogState = false)
                }
            }
        }
    }

    private fun init() {
        viewModelScope.launch {
            mypageRepository.getUserInfo()
                .onSuccess { userInfo ->
                    reduce {
                        copy(
                            userInfo = userInfo,
                            inputNickname = userInfo.nickname
                        )
                    }
                }
                .onFailure {
                    Log.i("getUser fail", "")
                }
            mypageRepository.getPersonaList()
                .onSuccess { personaList ->
                    Log.d("persona: ", personaList.toString())
                    reduce { copy(personaList = personaList) }
                    reduce {
                        val selectedIndex =
                            personaList.indexOfFirst { it.personaType == userInfo.persona }
                        copy(selectedPersonaIndex = selectedIndex)
                    }
                }
            nicknameFlow
                .debounce(500)
                .filter { it.isNotBlank() }
                .collect { nickname ->
                    mypageRepository.checkNicknameDuplicate(nickname)
                        .onSuccess { isDuplicate ->
                            reduce { copy(isNicknameDuplicate = isDuplicate) }
                        }.onFailure {
                            reduce { copy(isNicknameDuplicate = false) }
                        }
                }
        }
    }
}