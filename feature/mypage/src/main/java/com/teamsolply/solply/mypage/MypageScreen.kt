package com.teamsolply.solply.mypage

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
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.teamsolply.solply.designsystem.component.dialog.SolplyConfirmDialog
import com.teamsolply.solply.designsystem.theme.SolplyTheme
import com.teamsolply.solply.mypage.component.EmptyPlaceContainer
import com.teamsolply.solply.mypage.component.MypageSettingItem
import com.teamsolply.solply.mypage.component.SavedPlaceListContainer
import com.teamsolply.solply.mypage.model.PlaceInfoEntity
import com.teamsolply.solply.ui.extension.customClickable
import com.teamsolply.solply.ui.lifecycle.LaunchedEffectWithLifecycle
import kotlinx.coroutines.flow.collectLatest

@Composable
fun MypageRoute(
    paddingValues: PaddingValues,
    navigateToBack: () -> Unit,
    navigateToProfile: () -> Unit,
    navigateToWithdraw: () -> Unit,
    viewModel: MypageViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.sendIntent(MypageIntent.Init)
    }

    LaunchedEffectWithLifecycle {
        viewModel.sideEffect.collectLatest { sideEffect ->
            when (sideEffect) {
                MypageSideEffect.NavigateToBack -> navigateToBack()
                MypageSideEffect.NavigateToProfile -> navigateToProfile()
                MypageSideEffect.NavigateToWithdraw -> navigateToWithdraw()
            }
        }
    }

    MypageScreen(
        nickname = uiState.userInfo.nickname,
        savedPlaceList = uiState.placeList,
        dialogState = uiState.dialogState,
        onBackButtonClick = navigateToBack,
        onProfileEditClick = { viewModel.sendIntent(MypageIntent.ProfileEditClick) },
        onLogOutClick = { viewModel.sendIntent(MypageIntent.LogOutButtonClick) },
        onWithdrawClick = { viewModel.sendIntent(MypageIntent.WithdrawClick) },
        onDialogConfirmClick = { viewModel.sendIntent(MypageIntent.DialogConfirmClick) },
        onDialogDismissClick = { viewModel.sendIntent(MypageIntent.DialogDismissClick) },
        modifier = Modifier.padding(paddingValues)
    )
}

@Composable
fun MypageScreen(
    nickname: String,
    savedPlaceList: List<PlaceInfoEntity>,
    dialogState: Boolean,
    onBackButtonClick: () -> Unit,
    onProfileEditClick: () -> Unit,
    onLogOutClick: () -> Unit,
    onWithdrawClick: () -> Unit,
    onDialogConfirmClick: () -> Unit,
    onDialogDismissClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    if (dialogState) {
        SolplyConfirmDialog(
            text = stringResource(R.string.logout_dialog),
            confirmButtonText = stringResource(R.string.logout_dialog_confirm),
            dismissButtonText = stringResource(R.string.dialog_cancel),
            onClickConfirm = onDialogConfirmClick,
            onClickDismiss = onDialogDismissClick
        )
    }
    Column(
        modifier
            .fillMaxSize()
            .background(color = SolplyTheme.colors.gray100),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(com.teamsolply.solply.designsystem.R.drawable.ic_back),
                contentDescription = "back",
                tint = Color.Unspecified,
                modifier = Modifier
                    .padding(start = 16.dp, bottom = 16.dp, top = 11.dp)
                    .customClickable(rippleEnabled = false) { onBackButtonClick() }
            )
        }
        Image(
            painter = painterResource(R.drawable.img_basic_profile),
            contentDescription = "기본 프로필",
            modifier = Modifier
                .width(80.dp)
                .height(80.dp)
                .clip(CircleShape),
            contentScale = ContentScale.Fit
        )
        Spacer(modifier = Modifier.height(12.dp))
        Text(
            text = nickname,
            color = SolplyTheme.colors.black,
            style = SolplyTheme.typography.display20Sb.copy(
                lineHeightStyle = LineHeightStyle(
                    alignment = LineHeightStyle.Alignment.Center,
                    trim = LineHeightStyle.Trim.None
                )
            )
        )
        Spacer(modifier = Modifier.height(12.dp))
        Row(
            modifier = Modifier
                .customClickable(
                    rippleEnabled = false,
                    onClick = onProfileEditClick
                ),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = stringResource(R.string.profile_edit),
                color = SolplyTheme.colors.gray600,
                style = SolplyTheme.typography.button14M,
                modifier = Modifier.padding(start = 16.dp)
            )
            Icon(
                painter = painterResource(com.teamsolply.solply.designsystem.R.drawable.ic_next_arrow),
                contentDescription = "",
                tint = SolplyTheme.colors.gray600
            )
        }
        Spacer(modifier = Modifier.height(44.dp))
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = SolplyTheme.colors.white),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 16.dp)
            ) {
                Text(
                    text = "내가 등록한 장소",
                    color = SolplyTheme.colors.black,
                    style = SolplyTheme.typography.body16M
                )
            }
            if (savedPlaceList.isEmpty()) {
                EmptyPlaceContainer(
                    modifier = Modifier
                        .padding(bottom = 16.dp)
                )
            } else {
                SavedPlaceListContainer(
                    savedPlaceList = savedPlaceList,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 20.dp, bottom = 16.dp)
                )
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = SolplyTheme.colors.white),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp, start = 20.dp, end = 16.dp, bottom = 12.dp)
            ) {
                Text(
                    text = stringResource(R.string.mypage_account_setting),
                    color = SolplyTheme.colors.black,
                    style = SolplyTheme.typography.body16M
                )
            }
            MypageSettingItem(
                text = stringResource(R.string.mypage_customer_service),
                onClick = { /* TODO */ },
                isBorderEnabled = true
            )
            MypageSettingItem(
                text = "로그인 정보",
                onClick = { /* TODO */ },
                info = "카카오 로그인",
                isBorderEnabled = true
            )
            MypageSettingItem(
                text = "앱 버전",
                onClick = { /* TODO */ },
                info = "v 1.0.0",
                isBorderEnabled = true
            )
            MypageSettingItem(
                text = stringResource(R.string.mypage_logout),
                onClick = onLogOutClick,
                isBorderEnabled = true
            )
            MypageSettingItem(
                text = stringResource(R.string.mypage_withdraw),
                onClick = onWithdrawClick,
                isBorderEnabled = false
            )
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

@Composable
@Preview
private fun MypageScreenPreview() {
    SolplyTheme {
        MypageScreen(
            nickname = "닉네임",
            savedPlaceList = emptyList(),
            dialogState = false,
            onBackButtonClick = {},
            onProfileEditClick = {},
            onLogOutClick = {},
            onWithdrawClick = {},
            onDialogConfirmClick = {},
            onDialogDismissClick = {}
        )
    }
}
