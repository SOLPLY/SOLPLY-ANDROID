package com.teamsolply.solply.onboarding

import com.teamsolply.solply.ui.base.SideEffect
import com.teamsolply.solply.ui.base.UiIntent
import com.teamsolply.solply.ui.base.UiState

data class OnBoardingState(
    val currentPage: Int = 0,
    val totalPageCount: Int = 3,
    val townList: List<Town> = listOf(
        Town(id = 0L, name = "망원동"),
        Town(id = 1L, name = "연희동")
    ),
    val selectedTownId: Long? = null,
    val personaList: List<Persona> = listOf(
        Persona(type = "REST", description = "조용한 공간에 오래 머물고 싶어요"),
        Persona(type = "EXPLORE", description = "이곳저곳 둘러보고 싶어요"),
        Persona(type = "MOODING", description = "취향이 담긴 곳을 찾고 싶어요"),
        Persona(type = "NATURAL", description = "자연을 감상하며 쉬고 싶어요")
    ),
    val selectedPersona: Persona? = null,

    val userNickname: String = "",
    val isNicknameDuplicate: Boolean = false,
    val showStartingScreen: Boolean = false
) : UiState

sealed interface OnBoardingIntent : UiIntent {
    data object OnBoardingButtonClick : OnBoardingIntent
    data class OnPageChanged(val newPage: Int) : OnBoardingIntent
    data class OnTownChanged(val newPage: Int) : OnBoardingIntent
    data class OnTownSelected(val townId: Long) : OnBoardingIntent
    data class OnPersonaChanged(val newPage: Int) : OnBoardingIntent
    data class OnPersonaSelected(val persona: Persona) : OnBoardingIntent
    data class ChangeInputNickname(val nickname: String) : OnBoardingIntent
    data object ShowStartingScreen : OnBoardingIntent
}

sealed interface OnBoardingSideEffect : SideEffect {
    data object NavigateToPlace : OnBoardingSideEffect
}

data class Town(val id: Long, val name: String)
data class Persona(val type: String, val description: String)
