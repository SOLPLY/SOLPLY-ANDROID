package com.teamsolply.solply.mypage.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.teamsolply.solply.designsystem.component.button.SolplyBasicButton
import com.teamsolply.solply.designsystem.theme.SolplyTheme


@Composable
fun EmptyCollectionScreen(
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "저장한 장소가 없어요.",
            style = SolplyTheme.typography.body16M,
            color = SolplyTheme.colors.gray800
        )
        Spacer(
            modifier = Modifier
                .height(28.dp)
        )
        SolplyBasicButton(
            text = "나만의 장소 수집하러 가기",
            onClick = onClick,
            textColor = SolplyTheme.colors.purple700,
            enabledBackgroundColor = SolplyTheme.colors.purple300,
            modifier = Modifier.padding(horizontal = 64.dp)
        )
    }
}