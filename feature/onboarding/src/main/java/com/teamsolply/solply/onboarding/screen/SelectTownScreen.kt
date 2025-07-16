package com.teamsolply.solply.onboarding.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.teamsolply.solply.designsystem.component.button.AddLocalAreaButton
import com.teamsolply.solply.designsystem.component.button.SolplyBasicButton
import com.teamsolply.solply.designsystem.theme.SolplyTheme
import com.teamsolply.solply.onboarding.OnBoardingIntent
import com.teamsolply.solply.onboarding.OnBoardingState

@Composable
fun SelectTownScreen(
    state: OnBoardingState,
    onNextClick: () -> Unit,
    onBoardingIntent: (OnBoardingIntent) -> Unit
) {
    val townList = state.townList

    val isButtonEnabled = state.selectedTownId != null
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 20.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column(
            modifier = Modifier
        ) {
            Text(
                text = "반가워요!\n가장 먼저 추천 받고 싶은 동네를\n선택해주세요.",
                style = SolplyTheme.typography.display20Sb,
                color = SolplyTheme.colors.black,
                modifier = Modifier
                    .padding(top = 24.dp)
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 28.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                townList.take(2).forEach { town ->
                    AddLocalAreaButton(
                        text = town.name,
                        onClick = { onBoardingIntent(OnBoardingIntent.OnTownSelected(town.id)) },
                        isShowMore = false,
                        selected = state.selectedTownId == town.id,
                        modifier = Modifier
                            .weight(1f)
                            .aspectRatio(1f)
                    )
                }

                AddLocalAreaButton(
                    text = "",
                    onClick = { },
                    isShowMore = true,
                    selected = false,
                    modifier = Modifier
                        .weight(1f)
                        .aspectRatio(1f)
                )
            }
        }

        SolplyBasicButton(
            text = "다음",
            modifier = Modifier
                .padding(bottom = 24.dp),
            onClick = {
                if (isButtonEnabled) {
                    onNextClick()
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
