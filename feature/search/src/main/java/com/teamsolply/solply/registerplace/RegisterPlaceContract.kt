package com.teamsolply.solply.registerplace

import com.teamsolply.solply.ui.base.SideEffect
import com.teamsolply.solply.ui.base.UiIntent
import com.teamsolply.solply.ui.base.UiState

data class RegisterPlaceState(
    val placeName: String = ""
) : UiState

sealed interface RegisterPlaceIntent : UiIntent {
    data class InputPlaceNameText(
        val text: String
    ) : RegisterPlaceIntent
}

sealed interface RegisterPlaceSideEffect : SideEffect