package com.teamsolply.solply.mypage.withdraw

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.teamsolply.solply.designsystem.component.button.SolplyBasicButton
import com.teamsolply.solply.designsystem.component.dialog.SolplyConfirmDialog
import com.teamsolply.solply.designsystem.component.textfield.SolplyFixedReportTextField
import com.teamsolply.solply.designsystem.component.topbar.SolplyTopBar
import com.teamsolply.solply.designsystem.theme.SolplyTheme
import com.teamsolply.solply.mypage.R
import com.teamsolply.solply.mypage.model.WithdrawEntity
import com.teamsolply.solply.mypage.model.WithdrawType
import com.teamsolply.solply.ui.extension.customClickable
import com.teamsolply.solply.ui.lifecycle.LaunchedEffectWithLifecycle
import kotlinx.coroutines.flow.collectLatest

@Composable
fun WithdrawRoute(
    paddingValues: PaddingValues,
    navigateToBack: () -> Unit,
    navigateToOauth: () -> Unit,
    viewModel: WithdrawViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.sendIntent(WithdrawIntent.Init)
    }

    LaunchedEffectWithLifecycle {
        viewModel.sideEffect.collectLatest { sideEffect ->
            when (sideEffect) {
                WithdrawSideEffect.NavigateToBack -> navigateToBack()
                WithdrawSideEffect.NavigateToOauth -> navigateToOauth()
            }
        }
    }

    WithdrawScreen(
        withdrawList = uiState.withdrawList,
        withdrawReason = uiState.withdrawReason,
        selectedIndex = uiState.selectedIndex,
        buttonEnabled = uiState.buttonEnabled,
        dialogState = uiState.dialogState,
        onWithdrawItemClick = { viewModel.sendIntent(WithdrawIntent.WithdrawItemClick(it)) },
        onTextFieldValueChange = { viewModel.sendIntent(WithdrawIntent.WithdrawReasonInput(it)) },
        onWithdrawButtonClick = { viewModel.sendIntent(WithdrawIntent.WithdrawButtonClick) },
        onDialogConfirmClick = { viewModel.sendIntent(WithdrawIntent.DialogConfirmClick) },
        onDialogDismissClick = { viewModel.sendIntent(WithdrawIntent.DialogDismissClick) },
        onBackButtonClick = { viewModel.sendIntent(WithdrawIntent.BackButtonClick) },
        modifier = Modifier.padding(paddingValues)
    )
}

@Composable
fun WithdrawScreen(
    withdrawList: List<WithdrawEntity>,
    withdrawReason: String,
    selectedIndex: Int,
    buttonEnabled: Boolean,
    dialogState: Boolean,
    onWithdrawItemClick: (Int) -> Unit,
    onTextFieldValueChange: (String) -> Unit,
    onWithdrawButtonClick: () -> Unit,
    onDialogConfirmClick: () -> Unit,
    onDialogDismissClick: () -> Unit,
    onBackButtonClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    if (dialogState) {
        SolplyConfirmDialog(
            text = stringResource(R.string.mypage_withdraw),
            confirmButtonText = stringResource(R.string.withdraw_dialog_confirm),
            dismissButtonText = stringResource(R.string.dialog_cancel),
            onClickConfirm = onDialogConfirmClick,
            onClickDismiss = onDialogDismissClick
        )
    }
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(SolplyTheme.colors.white)
            .padding(top = 50.dp),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        LazyColumn {
            item {
                SolplyTopBar(
                    barText = stringResource(R.string.mypage_withdraw),
                    onBackButtonClick = onBackButtonClick
                )
            }
            itemsIndexed(withdrawList) { index, item ->
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp)
                        .customClickable(
                            rippleEnabled = false,
                            onClick = { onWithdrawItemClick(index) }
                        ),
                    verticalArrangement = Arrangement.Top,
                    horizontalAlignment = Alignment.Start
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = item.description,
                            style = if (index == selectedIndex) SolplyTheme.typography.body16M else SolplyTheme.typography.body16R,
                            color = SolplyTheme.colors.black,
                            modifier = Modifier.padding(vertical = 11.dp)
                        )
                        if (index == selectedIndex) {
                            Icon(
                                painter = painterResource(com.teamsolply.solply.designsystem.R.drawable.ic_filter_selected),
                                contentDescription = "",
                                tint = Color.Unspecified
                            )
                        }
                    }
                    if (index == withdrawList.lastIndex && selectedIndex == withdrawList.lastIndex) {
                        SolplyFixedReportTextField(
                            value = withdrawReason,
                            onValueChange = { onTextFieldValueChange(it) }
                        )
                    } else {
                        HorizontalDivider(
                            thickness = 1.dp,
                            color = SolplyTheme.colors.gray200
                        )
                    }
                }
            }
        }
        SolplyBasicButton(
            text = stringResource(R.string.mypage_withdraw),
            onClick = {
                if (buttonEnabled) {
                    onWithdrawButtonClick()
                }
            },
            enabledBackgroundColor = if (buttonEnabled) {
                SolplyTheme.colors.gray900
            } else {
                SolplyTheme.colors.gray300
            },
            textColor = if (buttonEnabled) {
                SolplyTheme.colors.white
            } else {
                SolplyTheme.colors.gray800
            },
            modifier = Modifier.padding(bottom = 24.dp, start = 20.dp, end = 20.dp)
        )
    }
}

@Preview
@Composable
private fun WithdrawScreenPreview() {
    SolplyTheme {
        WithdrawScreen(
            withdrawList = listOf(
                WithdrawEntity(
                    withdrawType = WithdrawType.NOT_USE,
                    description = "자주 사용하지 않아서"
                ),
                WithdrawEntity(
                    withdrawType = WithdrawType.NOT_USE,
                    description = "원하는 지역과 장소가 부족해서"
                ),
                WithdrawEntity(
                    withdrawType = WithdrawType.OTHERS,
                    description = "기타 (직접 입력)"
                )
            ),
            withdrawReason = "",
            selectedIndex = 2,
            buttonEnabled = false,
            dialogState = false,
            onWithdrawItemClick = {},
            onWithdrawButtonClick = {},
            onDialogConfirmClick = {},
            onTextFieldValueChange = {},
            onDialogDismissClick = {},
            onBackButtonClick = {},
            modifier = Modifier.fillMaxSize()
        )
    }
}
