package com.teamsolply.solply.registerplace

import com.teamsolply.solply.ui.base.SideEffect
import com.teamsolply.solply.ui.base.UiIntent
import com.teamsolply.solply.ui.base.UiState

data class RegisterPlaceState(
    val d: String = ""
) : UiState

sealed interface RegisterPlaceIntent : UiIntent {
}

sealed interface RegisterPlaceSideEffect : SideEffect