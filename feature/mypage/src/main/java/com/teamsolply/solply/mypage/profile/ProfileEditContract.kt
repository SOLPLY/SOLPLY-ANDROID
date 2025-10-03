package com.teamsolply.solply.mypage.profile

import com.teamsolply.solply.model.Persona
import com.teamsolply.solply.mypage.model.PersonaEntity
import com.teamsolply.solply.mypage.model.SelectedTownInfo
import com.teamsolply.solply.mypage.model.UserInfo
import com.teamsolply.solply.ui.base.SideEffect
import com.teamsolply.solply.ui.base.UiIntent
import com.teamsolply.solply.ui.base.UiState

data class ProfileEditState(
    val userInfo: UserInfo = UserInfo(
        userId = 1,
        nickname = "숭이숭이숭이",
        selectedTown = SelectedTownInfo(0, "망원동"),
        persona = Persona.REST
    ),
    val personaList: List<PersonaEntity> = emptyList(),
    val selectedPersonaIndex: Int = -1,
) : UiState

sealed interface ProfileEditIntent : UiIntent {
    data object Init : ProfileEditIntent

    data object CompleteButtonClick : ProfileEditIntent
}

sealed interface ProfileEditSideEffect : SideEffect {
    data object NavigateToBack : ProfileEditSideEffect
    data object NavigateToProfile : ProfileEditSideEffect
    data object NavigateToMypage : ProfileEditSideEffect
}
