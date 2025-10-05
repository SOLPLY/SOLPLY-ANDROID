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
        persona = Persona.REST,
        profileImageUrl = ""
    ),
    val inputNickname: String = "",
    val isNicknameDuplicate: Boolean = false,
    val isEditSuccess: Boolean = false,
    val personaList: List<PersonaEntity> = emptyList(),
    val selectedPersonaIndex: Int = -1,
    val isDropped: Boolean = false,
    val completeButtonEnabled: Boolean = false,
    val dialogState: Boolean = false,
) : UiState

sealed interface ProfileEditIntent : UiIntent {
    data object Init : ProfileEditIntent

    data class ChangeInputNickname(val nickname: String) : ProfileEditIntent
    data object ValidateNickname : ProfileEditIntent
    data class ChangeEditingSuccess(val state: Boolean) : ProfileEditIntent

    data object DropDownIconClick : ProfileEditIntent
    data class DropDownItemClick(val index: Int) : ProfileEditIntent
    data object CompleteButtonClick : ProfileEditIntent
    data object BackButtonClick : ProfileEditIntent

    data object DialogConfirmClick : ProfileEditIntent
    data object DialogDismissClick : ProfileEditIntent
}

sealed interface ProfileEditSideEffect : SideEffect {
    data object NavigateToBack : ProfileEditSideEffect
    data object NavigateToMypage : ProfileEditSideEffect
}
