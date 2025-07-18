package com.teamsolply.solply.onboarding.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.teamsolply.solply.designsystem.component.button.SolplyBasicButton
import com.teamsolply.solply.designsystem.theme.SolplyTheme
import com.teamsolply.solply.onboarding.OnBoardingIntent
import com.teamsolply.solply.onboarding.OnBoardingState
import com.teamsolply.solply.onboarding.component.MoodOptionBox

@Composable
fun SelectPersonaScreen(
    state: OnBoardingState,
    onNextClick: () -> Unit,
    onBoardingIntent: (OnBoardingIntent) -> Unit
) {
    val options = state.personaList
    val selectedPersona = state.selectedPersona
    val isButtonEnabled = selectedPersona != null

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 20.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column {
            Text(
                text = "혼자만의 시간,\n어떻게 보내고 싶나요?",
                style = SolplyTheme.typography.display20Sb,
                color = SolplyTheme.colors.black,
                modifier = Modifier.padding(top = 24.dp)
            )

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 28.dp)
            ) {
                options.personaList
                    .drop(1)
                    .forEach { persona ->
                    val isSelected = selectedPersona == persona.personaType
                    MoodOptionBox(
                        text = persona.description,
                        selected = isSelected,
                        onClick = {
                            onBoardingIntent(OnBoardingIntent.OnPersonaSelected(persona.personaType))
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 6.dp)
                            .height(52.dp)
                    )
                }
            }
        }

        SolplyBasicButton(
            text = "다음",
            modifier = Modifier.padding(bottom = 24.dp),
            onClick = {
                if (isButtonEnabled) onNextClick()
            },
            selected = isButtonEnabled,
            textStyle = SolplyTheme.typography.button16M,
            textColor = if (isButtonEnabled) SolplyTheme.colors.white else SolplyTheme.colors.gray800,
            enabledBackgroundColor = SolplyTheme.colors.gray900,
            disabledBackgroundColor = SolplyTheme.colors.gray300
        )
    }
}
