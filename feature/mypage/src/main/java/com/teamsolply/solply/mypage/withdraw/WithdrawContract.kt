package com.teamsolply.solply.mypage.withdraw

import com.teamsolply.solply.mypage.model.WithdrawEntity
import com.teamsolply.solply.ui.base.SideEffect
import com.teamsolply.solply.ui.base.UiIntent
import com.teamsolply.solply.ui.base.UiState

data class WithdrawState(
    val withdrawList: List<WithdrawEntity> = emptyList(),
    val dialogState: Boolean = false,
    val buttonEnabled: Boolean = false
) : UiState

sealed interface WithdrawIntent : UiIntent {
    data object Init : WithdrawIntent

    data object DialogConfirmClick : WithdrawIntent
    data object DialogDismissClick : WithdrawIntent
}

sealed interface WithdrawSideEffect : SideEffect {
    data object NavigateToBack : WithdrawSideEffect
    data object NavigateToProfile : WithdrawSideEffect
}
