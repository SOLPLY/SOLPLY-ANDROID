package com.teamsolply.solply.onboarding.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.dp
import com.teamsolply.solply.designsystem.component.button.SolplyBasicButton
import com.teamsolply.solply.designsystem.component.textfield.SolplyNicknameTextField
import com.teamsolply.solply.designsystem.theme.SolplyTheme
import com.teamsolply.solply.onboarding.OnBoardingIntent
import com.teamsolply.solply.onboarding.OnBoardingState
import com.teamsolply.solply.ui.extension.addFocusCleaner

@Composable
fun NamingScreen(
    state: OnBoardingState,
    inputNickname: String,
    isNicknameDuplicate: Boolean,
    changeInputNickname: (String) -> Unit,
    onNextClick: () -> Unit,
    checkOnBoardingSuccess: (Boolean) -> Unit,
    onBoardingIntent: (OnBoardingIntent) -> Unit
) {
    val focusManager = LocalFocusManager.current

    Box(
        modifier = Modifier
            .fillMaxSize()
            .addFocusCleaner(focusManager)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
                .align(Alignment.TopStart)
        ) {
            Text(
                text = "솔플리와 함께할 준비 되셨나요?\n닉네임을 알려주세요.",
                style = SolplyTheme.typography.display20Sb,
                color = SolplyTheme.colors.black,
                modifier = Modifier
                    .padding(top = 24.dp, bottom = 28.dp)
            )

            SolplyNicknameTextField(
                value = inputNickname,
                isNicknameDuplicate = isNicknameDuplicate,
                onValueChange = { changeInputNickname(it) },
                changeNicknameValidate = { checkOnBoardingSuccess(it) },
                checkNicknameValidate = { input ->
                    input.all { it.isLetterOrDigit() }
                }
            )
        }

        SolplyBasicButton(
            text = "다음",
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .padding(start = 20.dp, end = 20.dp, bottom = 24.dp),
            onClick = { onBoardingIntent(OnBoardingIntent.ShowStartingScreen) },
            selected = state.isOnBoardingSuccess,
            textStyle = SolplyTheme.typography.button16M,
            textColor = if (state.isOnBoardingSuccess) SolplyTheme.colors.white else SolplyTheme.colors.gray800,
            enabledBackgroundColor = SolplyTheme.colors.gray900,
            disabledBackgroundColor = SolplyTheme.colors.gray300
        )
    }
}
