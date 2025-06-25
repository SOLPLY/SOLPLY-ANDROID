package com.teamsolply.solply.onboarding

import com.teamsolply.solply.ui.base.SideEffect
import com.teamsolply.solply.ui.base.UiIntent
import com.teamsolply.solply.ui.base.UiState

data class OnBoardingState(
    val g: String = ""
) : UiState

sealed interface OnBoardingIntent : UiIntent {
    data object OnBoardingButtonClick : OnBoardingIntent
}

sealed interface OnBoardingSideEffect : SideEffect {
    data object NavigateToPlace : OnBoardingSideEffect
}
