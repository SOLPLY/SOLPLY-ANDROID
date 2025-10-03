package com.teamsolply.solply.mypage.profile

import androidx.lifecycle.viewModelScope
import com.teamsolply.solply.mypage.repository.MypageRepository
import com.teamsolply.solply.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileEditViewModel @Inject constructor(
    private val mypageRepository: MypageRepository
) :
    BaseViewModel<ProfileEditState, ProfileEditIntent, ProfileEditSideEffect>(ProfileEditState()) {

    override fun handleIntent(intent: ProfileEditIntent) {
        when (intent) {
            ProfileEditIntent.Init -> init()

            ProfileEditIntent.CompleteButtonClick -> TODO()
        }
    }

    private fun init() {
        viewModelScope.launch {
            mypageRepository.getUserInfo()
                .onSuccess { userInfo ->
                    reduce { copy(userInfo = userInfo) }
                }
            mypageRepository.getPersonaList()
                .onSuccess { personaList ->
                    reduce { copy(personaList = personaList) }
                }
            reduce {
                val selectedIndex = personaList.indexOfFirst { it.personaType == userInfo.persona }
                copy(selectedPersonaIndex = selectedIndex)
            }
        }
    }
}