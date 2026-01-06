package com.teamsolply.solply.mypage.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.teamsolply.solply.designsystem.component.button.SolplyBasicButton
import com.teamsolply.solply.designsystem.component.dialog.SolplyConfirmDialog
import com.teamsolply.solply.designsystem.component.textfield.SolplyNicknameTextField
import com.teamsolply.solply.designsystem.component.topbar.SolplyTopBar
import com.teamsolply.solply.designsystem.theme.SolplyTheme
import com.teamsolply.solply.mypage.R
import com.teamsolply.solply.mypage.component.SolplyPersonaDropDown
import com.teamsolply.solply.mypage.model.PersonaEntity
import com.teamsolply.solply.ui.lifecycle.LaunchedEffectWithLifecycle
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.flow.collectLatest

@Composable
fun ProfileRoute(
    paddingValues: PaddingValues,
    navigateToBack: () -> Unit,
    navigateToMypage: () -> Unit,
    viewModel: ProfileEditViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.sendIntent(ProfileEditIntent.Init)
    }

    LaunchedEffectWithLifecycle {
        viewModel.sideEffect.collectLatest { sideEffect ->
            when (sideEffect) {
                ProfileEditSideEffect.NavigateToMypage -> {
                    navigateToMypage()
                }

                ProfileEditSideEffect.NavigateToBack -> {
                    navigateToBack()
                }
            }
        }
    }

    ProfileEditScreen(
        nickname = uiState.inputNickname,
        isNicknameDuplicated = uiState.isNicknameDuplicate,
        selectedPersonaIndex = uiState.selectedPersonaIndex,
        personaList = uiState.personaList,
        dialogState = uiState.dialogState,
        isDropped = uiState.isDropped,
        isEditSuccess = uiState.isEditSuccess,
        onNicknameChanged = { viewModel.sendIntent(ProfileEditIntent.ChangeInputNickname(it)) },
        onNicknameValidateChanged = { viewModel.sendIntent(ProfileEditIntent.ChangeEditingSuccess(it)) },
        onDropIconClick = { viewModel.sendIntent(ProfileEditIntent.DropDownIconClick) },
        onDropDownItemClick = { viewModel.sendIntent(ProfileEditIntent.DropDownItemClick(it)) },
        onBackButtonClick = { viewModel.sendIntent(ProfileEditIntent.BackButtonClick) },
        onCompleteButtonClick = { viewModel.sendIntent(ProfileEditIntent.CompleteButtonClick) },
        onDialogConfirmClick = { viewModel.sendIntent(ProfileEditIntent.DialogConfirmClick) },
        onDialogDismissClick = { viewModel.sendIntent(ProfileEditIntent.DialogDismissClick) },
        modifier = Modifier.padding(paddingValues)
    )
}

@Composable
fun ProfileEditScreen(
    nickname: String,
    isNicknameDuplicated: Boolean,
    selectedPersonaIndex: Int,
    personaList: List<PersonaEntity>,
    dialogState: Boolean,
    isDropped: Boolean,
    isEditSuccess: Boolean,
    onNicknameChanged: (String) -> Unit,
    onNicknameValidateChanged: (Boolean) -> Unit,
    onDropIconClick: () -> Unit,
    onDropDownItemClick: (Int) -> Unit,
    onBackButtonClick: () -> Unit,
    onCompleteButtonClick: () -> Unit,
    onDialogConfirmClick: () -> Unit,
    onDialogDismissClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    if (dialogState) {
        SolplyConfirmDialog(
            text = stringResource(R.string.profile_dialog),
            confirmButtonText = stringResource(R.string.profile_dialog_confirm),
            dismissButtonText = stringResource(R.string.profile_dialog_cancel),
            onClickConfirm = onDialogConfirmClick,
            onClickDismiss = onDialogDismissClick
        )
    }
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(color = SolplyTheme.colors.white)
            .padding(top = 50.dp),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        SolplyTopBar(
            barText = stringResource(R.string.profile_edit),
            onBackButtonClick = onBackButtonClick
        )
        Spacer(
            modifier = Modifier.height(16.dp)
        )
        Image(
            painter = painterResource(R.drawable.img_basic_profile),
            contentDescription = stringResource(R.string.profile_img),
            modifier = Modifier
                .width(92.dp)
                .height(92.dp)
                .clip(CircleShape),
            contentScale = ContentScale.Fit
        )
        Spacer(modifier = Modifier.height(32.dp))
        Column(
            modifier = Modifier.padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start
            ) {
                Text(
                    text = stringResource(R.string.profile_nickname),
                    color = SolplyTheme.colors.black,
                    style = SolplyTheme.typography.body16M
                )
            }
            SolplyNicknameTextField(
                value = nickname,
                isNicknameDuplicate = isNicknameDuplicated,
                onValueChange = onNicknameChanged,
                changeNicknameValidate = onNicknameValidateChanged,
                checkNicknameValidate = { input ->
                    input.all { it.isLetterOrDigit() }
                },
                modifier = Modifier.padding(top = 12.dp)
            )
        }
        Spacer(modifier = Modifier.height(24.dp))
        Column(
            modifier = Modifier.padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start
            ) {
                Text(
                    text = stringResource(R.string.profile_solply_style),
                    color = SolplyTheme.colors.black,
                    style = SolplyTheme.typography.body16M
                )
            }
            SolplyPersonaDropDown(
                placeholder = stringResource(R.string.profile_persona_placeholder),
                isDropped = isDropped,
                onClickItem = onDropDownItemClick,
                onClickDropIcon = onDropIconClick,
                dropDownContents = personaList,
                selectedIndex = selectedPersonaIndex,
                isSelected = selectedPersonaIndex != -1,
                modifier = Modifier.padding(vertical = 12.dp)
            )
        }
        Spacer(modifier = Modifier.weight(12f))
        SolplyBasicButton(
            text = "완료",
            selected = isEditSuccess,
            onClick = { onCompleteButtonClick() },
            enabledBackgroundColor = SolplyTheme.colors.gray900,
            disabledBackgroundColor = SolplyTheme.colors.gray300,
            modifier = Modifier.padding(vertical = 24.dp, horizontal = 16.dp)
        )
    }
}

@Preview
@Composable
private fun ProfileScreenPreview() {
    SolplyTheme {
        ProfileEditScreen(
            nickname = "",
            isNicknameDuplicated = false,
            selectedPersonaIndex = -1,
            personaList = persistentListOf(),
            dialogState = false,
            isDropped = false,
            isEditSuccess = false,
            onNicknameChanged = {},
            onNicknameValidateChanged = {},
            onDropIconClick = {},
            onDropDownItemClick = {},
            onBackButtonClick = {},
            onCompleteButtonClick = {},
            onDialogConfirmClick = {},
            onDialogDismissClick = {}
        )
    }
}
