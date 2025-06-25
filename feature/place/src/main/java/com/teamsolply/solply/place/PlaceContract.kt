package com.teamsolply.solply.place

import com.teamsolply.solply.ui.base.SideEffect
import com.teamsolply.solply.ui.base.UiIntent
import com.teamsolply.solply.ui.base.UiState

data class PlaceState(
    val g: String = ""
) : UiState

sealed interface PlaceIntent : UiIntent

sealed interface PlaceSideEffect : SideEffect
