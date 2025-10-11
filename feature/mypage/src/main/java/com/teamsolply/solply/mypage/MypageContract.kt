package com.teamsolply.solply.mypage

import com.teamsolply.solply.model.Persona
import com.teamsolply.solply.mypage.model.PlaceInfoEntity
import com.teamsolply.solply.mypage.model.SelectedTownInfo
import com.teamsolply.solply.mypage.model.UserInfo
import com.teamsolply.solply.ui.base.SideEffect
import com.teamsolply.solply.ui.base.UiIntent
import com.teamsolply.solply.ui.base.UiState

data class MypageState(
    val userInfo: UserInfo = UserInfo(
        userId = 1,
        nickname = "숭이숭이숭이",
        selectedTown = SelectedTownInfo(0, "망원동"),
        persona = Persona.REST,
        profileImageUrl = ""
    ),
    val placeList: List<PlaceInfoEntity> = emptyList(),
    val placeAllState: Boolean = false,
    val dialogState: Boolean = false
) : UiState

sealed interface MypageIntent : UiIntent {
    data object Init : MypageIntent

    data object MypageBackButtonClick : MypageIntent
    data object PlaceAllBackButtonClick : MypageIntent

    data object LogOutButtonClick : MypageIntent

    data object LogOutDialogConfirmClick : MypageIntent
    data object LogOutDialogDismissClick : MypageIntent

    data object PlaceAllClick : MypageIntent
    data object ProfileEditClick : MypageIntent
    data object WithdrawClick : MypageIntent
}

sealed interface MypageSideEffect : SideEffect {
    data object NavigateToBack : MypageSideEffect
    data object NavigateToProfile : MypageSideEffect
    data object NavigateToWithdraw : MypageSideEffect
    data object NavigateToOauth : MypageSideEffect
}
