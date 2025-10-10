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

            WithdrawIntent.DialogConfirmClick -> TODO()
            WithdrawIntent.DialogDismissClick -> TODO()
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