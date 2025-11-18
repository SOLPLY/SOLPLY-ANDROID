package com.teamsolply.solply.onboarding

import com.teamsolply.solply.onboarding.model.PersonaEntity
import com.teamsolply.solply.onboarding.model.TownEntity
import com.teamsolply.solply.ui.base.SideEffect
import com.teamsolply.solply.ui.base.UiIntent
import com.teamsolply.solply.ui.base.UiState

data class OnBoardingState(
    val currentPage: Int = 0,
    val totalPageCount: Int = 4,
    val townList: TownEntity = TownEntity(
        parentTowns = emptyList()
    ),

    val selectedRegionId: Long? = 1,
    val selectedTownId: Long? = null,
    val townBottomSheetShown: Boolean = false,

    val personaList: PersonaEntity = PersonaEntity(personaList = emptyList()),
    val selectedPersona: String? = null,

    val userNickname: String = "",
    val isNicknameDuplicate: Boolean = false,
    val showStartingScreen: Boolean = false,
    val isOnBoardingSuccess: Boolean = false,
    val agree14: Boolean = false,
    val agreeService: Boolean = false,
    val agreePrivacy: Boolean = false
    ) : UiState


sealed interface OnBoardingIntent : UiIntent {
    data object OnBoardingButtonClick : OnBoardingIntent
    data object ChangeTownBottomSheetShown : OnBoardingIntent
    data class ChangeRegion(val regionId: Long) : OnBoardingIntent
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
    data class ChangeAgree14(val isChecked: Boolean) : OnBoardingIntent
    data class ChangeAgreeService(val isChecked: Boolean) : OnBoardingIntent
    data class ChangeAgreePrivacy(val isChecked: Boolean) : OnBoardingIntent

}

sealed interface OnBoardingSideEffect : SideEffect {
    data object NavigateToPlace : OnBoardingSideEffect
}
