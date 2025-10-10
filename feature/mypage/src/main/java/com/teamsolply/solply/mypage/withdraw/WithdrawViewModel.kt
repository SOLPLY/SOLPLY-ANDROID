package com.teamsolply.solply.mypage.withdraw

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

            WithdrawIntent.DialogConfirmClick -> TODO()
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

    private fun fetchWithdraw() {
        viewModelScope.launch {
            mypageRepository.deleteUser().onSuccess {
                // TODO
            }
        }
    }
}