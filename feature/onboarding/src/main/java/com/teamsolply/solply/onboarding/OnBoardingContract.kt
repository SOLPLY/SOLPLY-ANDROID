package com.teamsolply.solply.onboarding

import com.teamsolply.solply.onboarding.model.PersonaEntity
import com.teamsolply.solply.onboarding.model.TownEntity
import com.teamsolply.solply.ui.base.SideEffect
import com.teamsolply.solply.ui.base.UiIntent
import com.teamsolply.solply.ui.base.UiState

data class OnBoardingState(
    val currentPage: Int = 0,
    val totalPageCount: Int = 3,
    val townList: List<TownEntity> = emptyList(),
    val selectedTownId: Long? = null,
    val personaList: PersonaEntity = PersonaEntity(personaList = emptyList()),
    val selectedPersona: String? = null,

    val userNickname: String = "",
    val isNicknameDuplicate: Boolean = false,
    val showStartingScreen: Boolean = false,
    val isOnBoardingSuccess: Boolean = false
) : UiState

sealed interface OnBoardingIntent : UiIntent {
    data object OnBoardingButtonClick : OnBoardingIntent
    data class OnPageChanged(val newPage: Int) : OnBoardingIntent
    data class OnTownChanged(val newPage: Int) : OnBoardingIntent
    data class OnTownSelected(val townId: Long) : OnBoardingIntent
    data class OnPersonaChanged(val newPage: Int) : OnBoardingIntent
    data class OnPersonaSelected(val persona: String) : OnBoardingIntent
    data class ChangeInputNickname(val nickname: String) : OnBoardingIntent
    data class ChangeOnBoardingSuccess(
        val state: Boolean
    ) : OnBoardingIntent

    data object ShowStartingScreen : OnBoardingIntent
}

sealed interface OnBoardingSideEffect : SideEffect {
    data object NavigateToPlace : OnBoardingSideEffect
}
