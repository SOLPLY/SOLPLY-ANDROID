package com.teamsolply.solply.onboarding.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.teamsolply.solply.designsystem.component.button.SolplyBasicButton
import com.teamsolply.solply.designsystem.theme.SolplyTheme
import com.teamsolply.solply.onboarding.OnBoardingIntent
import com.teamsolply.solply.onboarding.OnBoardingState
import com.teamsolply.solply.ui.extension.customClickable

@Composable
fun AllowClauseScreen(
    state: OnBoardingState,
    onNextClick: () -> Unit,
    onBoardingIntent: (OnBoardingIntent) -> Unit
) {
    var isAllChecked by remember { mutableStateOf(false) }
    var agree14 by remember { mutableStateOf(false) }
    var agreeService by remember { mutableStateOf(false) }
    var agreePrivacy by remember { mutableStateOf(false) }

    LaunchedEffect(agree14, agreeService, agreePrivacy) {
        isAllChecked = agree14 && agreeService && agreePrivacy
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 20.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {

        Column(modifier = Modifier.padding(top = 40.dp)) {

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp)
                    .border(
                        width = 1.dp,
                        color = if (isAllChecked)
                            SolplyTheme.colors.red300
                        else
                            SolplyTheme.colors.gray300,
                        shape = RoundedCornerShape(20.dp)
                    )
                    .background(
                        color = if (isAllChecked)
                            SolplyTheme.colors.red100
                        else
                            SolplyTheme.colors.gray100,
                        shape = RoundedCornerShape(20.dp)
                    )
                    .customClickable(rippleEnabled = false) {
                        val toggle = !isAllChecked
                        isAllChecked = toggle
                        agree14 = toggle
                        agreeService = toggle
                        agreePrivacy = toggle
                    }
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 20.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "전체 동의",
                        style = SolplyTheme.typography.body16M,
                        color = SolplyTheme.colors.black
                    )

                    Image(
                        painter = painterResource(
                            id = if (isAllChecked)
                                com.teamsolply.solply.designsystem.R.drawable.ic_filter_selected
                            else
                                com.teamsolply.solply.designsystem.R.drawable.ic_select_before
                        ),
                        contentDescription = null
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            AgreementItem(
                text = "(필수) 만 14세 이상입니다",
                checked = agree14,
                onClick = { agree14 = !agree14 }
            )

            AgreementItem(
                text = "(필수) 서비스 이용 약관",
                checked = agreeService,
                onClick = { agreeService = !agreeService }
            )

            AgreementItem(
                text = "(필수) 개인정보 처리방침",
                checked = agreePrivacy,
                onClick = { agreePrivacy = !agreePrivacy }
            )
        }

        SolplyBasicButton(
            text = "다음",
            modifier = Modifier.padding(bottom = 24.dp),
            onClick = { if (isAllChecked) onNextClick() },
            selected = isAllChecked,
            textStyle = SolplyTheme.typography.button16M,
            textColor = if (isAllChecked) SolplyTheme.colors.white else SolplyTheme.colors.gray800,
            enabledBackgroundColor = SolplyTheme.colors.gray900,
            disabledBackgroundColor = SolplyTheme.colors.gray300
        )
    }
}

@Composable
fun AgreementItem(
    text: String,
    checked: Boolean,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(48.dp)     // ★ 첫 번째 코드 dp
            .customClickable(rippleEnabled = false) { onClick() },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(
                id = if (checked)
                    com.teamsolply.solply.designsystem.R.drawable.ic_agree_mini_option_checked
                else
                    com.teamsolply.solply.designsystem.R.drawable.ic_agree_mini_option_before_check
            ),
            contentDescription = null
        )

        Spacer(modifier = Modifier.width(8.dp))

        Text(
            text = text,
            style = SolplyTheme.typography.body14M,
            color = SolplyTheme.colors.black
        )
    }
}
