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
        TODO("Not yet implemented")
    }

    private fun getUserInfo() {
        viewModelScope.launch {
            mypageRepository.getUserInfo()
                .onSuccess { userInfo ->
                    reduce { copy(userInfo = userInfo) }
                }
        }
    }
}