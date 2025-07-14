package com.teamsolply.solply.onboarding.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.teamsolply.solply.designsystem.component.button.SolplyBasicButton
import com.teamsolply.solply.designsystem.component.textfield.SolplyNicknameTextField
import com.teamsolply.solply.designsystem.theme.SolplyTheme
import com.teamsolply.solply.onboarding.OnBoardingIntent
import com.teamsolply.solply.onboarding.OnBoardingState

@Composable
fun NamingScreen(
    state: OnBoardingState,
    onNextClick: () -> Unit,
    onBoardingIntent: (OnBoardingIntent) -> Unit
) {
    var nickname by remember { mutableStateOf("") }

    val isButtonEnabled = nickname.isNotBlank()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {

        Column {
            Text(
                text = "솔플리와 함께할 준비 되셨나요?\n닉네임을 알려주세요.",
                style = SolplyTheme.typography.display20Sb,
                color = SolplyTheme.colors.black,
                modifier = Modifier
                    .padding(top = 24.dp, bottom = 28.dp)
            )


            SolplyNicknameTextField(
                value = nickname,
                onValueChange = { nickname = it },
                onDuplicateCheck = { input ->
                    println("중복 체크 요청: $input")
                    input.lowercase() == "solply"
                },
                checkNicknameValidate = { input ->
                    input.all { it.isLetterOrDigit() }
                }
            )
        }

        SolplyBasicButton(
            text = "다음",
            modifier = Modifier
                .padding(bottom = 24.dp),
            onClick = {
                if (isButtonEnabled) {
                    onBoardingIntent(OnBoardingIntent.Nickname(nickname))
                    onBoardingIntent(OnBoardingIntent.ShowStartingScreen)
                }
            },
            selected = isButtonEnabled,
            textStyle = SolplyTheme.typography.button16M,
            textColor = if (isButtonEnabled) SolplyTheme.colors.white else SolplyTheme.colors.gray800,
            enabledBackgroundColor = SolplyTheme.colors.gray900,
            disabledBackgroundColor = SolplyTheme.colors.gray300
        )
    }
}