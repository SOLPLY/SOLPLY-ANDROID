package com.teamsolply.solply.mypage.withdraw

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.teamsolply.solply.mypage.repository.MypageRepository
import com.teamsolply.solply.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WithdrawViewModel @Inject constructor(
    private val mypageRepository: MypageRepository
) :
    BaseViewModel<WithdrawState, WithdrawIntent, WithdrawSideEffect>(WithdrawState()) {
    override fun handleIntent(intent: WithdrawIntent) {
        when (intent) {
            WithdrawIntent.Init -> {
                getWithdrawList()
            }

            WithdrawIntent.BackButtonClick -> {
                postSideEffect(WithdrawSideEffect.NavigateToBack)
            }

            is WithdrawIntent.WithdrawReasonInput -> {
                reduce {
                    copy(
                        withdrawReason = intent.reason,
                        buttonEnabled = intent.reason.isNotEmpty()
                    )
                }
            }

            is WithdrawIntent.WithdrawItemClick -> {
                reduce {
                    copy(
                        selectedIndex = intent.index,
                        buttonEnabled = intent.index != withdrawList.lastIndex
                    )
                }
            }

            WithdrawIntent.WithdrawButtonClick -> {
                reduce {
                    copy(
                        dialogState = true
                    )
                }
            }

            WithdrawIntent.DialogConfirmClick -> {
                val current = uiState.value
                viewModelScope.launch {
                    Log.d("withdraw:", "success")
                    mypageRepository.deleteUser(
                        withdrawType = current.withdrawList[current.selectedIndex].withdrawType,
                        reason = current.withdrawReason
                    ).onSuccess {
                        mypageRepository.saveAutoSignIn(false).onSuccess {
                            reduce {
                                copy(dialogState = false)
                            }
                            postSideEffect(WithdrawSideEffect.NavigateToOauth)
                        }
                    }
                }
            }

            WithdrawIntent.DialogDismissClick -> {
                reduce {
                    copy(dialogState = false)
                }
            }
        }
    }

    private fun getWithdrawList() {
        viewModelScope.launch {
            mypageRepository.getWithdrawList().onSuccess {
                reduce {
                    copy(withdrawList = it)
                }
            }
        }
    }
}