package com.teamsolply.solply.mypage

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.teamsolply.solply.mypage.repository.MypageRepository
import com.teamsolply.solply.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MypageViewModel @Inject constructor(
    private val mypageRepository: MypageRepository
) :
    BaseViewModel<MypageState, MypageIntent, MypageSideEffect>(MypageState()) {
    override fun handleIntent(intent: MypageIntent) {
        when (intent) {
            MypageIntent.Init -> getInitInfo()

            MypageIntent.LogOutButtonClick -> {
                reduce {
                    copy(
                        dialogState = true
                    )
                }
            }

            MypageIntent.LogOutDialogConfirmClick -> {
                viewModelScope.launch {
                    mypageRepository.saveAutoSignIn(false).onSuccess {
                        Log.d("logout:", "success")
                        reduce {
                            copy(dialogState = false)
                        }
                        postSideEffect(MypageSideEffect.NavigateToOauth)
                    }
                }
            }

            MypageIntent.LogOutDialogDismissClick -> {
                reduce {
                    copy(dialogState = false)
                }
            }

            MypageIntent.ProfileEditClick -> {
                postSideEffect(MypageSideEffect.NavigateToProfile)
            }

            MypageIntent.WithdrawClick -> {
                postSideEffect(MypageSideEffect.NavigateToWithdraw)
            }
        }
    }

    private fun getInitInfo() {
        viewModelScope.launch {
            mypageRepository.getUserInfo()
                .onSuccess { userInfo ->
                    reduce { copy(userInfo = userInfo) }

                    getPlaceList(
                        townId = userInfo.selectedTown.townId
                    )
                }
        }
    }

    private fun getPlaceList(townId: Long) {
        viewModelScope.launch {
            mypageRepository.getPlaceList(townId).onSuccess {
                reduce {
                    copy(
                        placeList = it.toPersistentList()
                    )
                }
            }
        }
    }
}
