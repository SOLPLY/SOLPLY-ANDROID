package com.teamsolply.solply.onboarding.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
    val agree14 = state.agree14
    val agreeService = state.agreeService
    val agreePrivacy = state.agreePrivacy
    val isAllChecked = agree14 && agreeService && agreePrivacy

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
                        color = if (isAllChecked) {
                            SolplyTheme.colors.red300
                        } else {
                            SolplyTheme.colors.gray300
                        },
                        shape = RoundedCornerShape(20.dp)
                    )
                    .background(
                        color = if (isAllChecked) {
                            SolplyTheme.colors.red100
                        } else {
                            SolplyTheme.colors.gray100
                        },
                        shape = RoundedCornerShape(20.dp)
                    )
                    .customClickable(rippleEnabled = false) {
                        val toggle = !isAllChecked
                        onBoardingIntent(OnBoardingIntent.ChangeAgree14(toggle))
                        onBoardingIntent(OnBoardingIntent.ChangeAgreeService(toggle))
                        onBoardingIntent(OnBoardingIntent.ChangeAgreePrivacy(toggle))
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
                            id = if (isAllChecked) {
                                com.teamsolply.solply.designsystem.R.drawable.ic_filter_selected
                            } else {
                                com.teamsolply.solply.designsystem.R.drawable.ic_select_before
                            }
                        ),
                        contentDescription = null
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            AgreementItem(
                text = "(필수) 만 14세 이상입니다",
                checked = agree14,
                onClick = {
                    onBoardingIntent(OnBoardingIntent.ChangeAgree14(!agree14))
                }
            )

            AgreementItem(
                text = "(필수) 서비스 이용 약관",
                checked = agreeService,
                onClick = {
                    onBoardingIntent(OnBoardingIntent.ChangeAgreeService(!agreeService))
                },
                showArrow = true,
                onArrowClick = {
                    // TODO: 서비스 이용 약관 이동
                }
            )

            AgreementItem(
                text = "(필수) 개인정보 처리방침",
                checked = agreePrivacy,
                onClick = {
                    onBoardingIntent(OnBoardingIntent.ChangeAgreePrivacy(!agreePrivacy))
                },
                showArrow = true,
                onArrowClick = {
                    // TODO: 개인정보 처리방침 이동
                }
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
    onClick: () -> Unit,
    showArrow: Boolean = false,
    onArrowClick: (() -> Unit)? = null
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(48.dp)
            .customClickable(rippleEnabled = false) { onClick() },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(
                id = if (checked) {
                    com.teamsolply.solply.designsystem.R.drawable.ic_agree_mini_option_checked
                } else {
                    com.teamsolply.solply.designsystem.R.drawable.ic_agree_mini_option_before_check
                }
            ),
            contentDescription = null,
            modifier = Modifier.size(20.dp)
        )

        Spacer(modifier = Modifier.width(8.dp))

        Text(
            text = text,
            style = SolplyTheme.typography.body14M,
            color = SolplyTheme.colors.black,
            modifier = Modifier.weight(1f)
        )

        if (showArrow) {
            Image(
                painter = painterResource(
                    id = com.teamsolply.solply.designsystem.R.drawable.ic_arrow_right
                ),
                contentDescription = null,
                modifier = Modifier
                    .size(16.dp)
                    .padding(start = 8.dp)
                    .customClickable(rippleEnabled = false) {
                        onArrowClick?.invoke()
                    }
            )
        }
    }
}
