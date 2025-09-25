package com.teamsolply.solply.mypage.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.teamsolply.solply.designsystem.theme.SolplyTheme

@Composable
fun EmptyPlaceContainer(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .padding(bottom = 16.dp)
            .height(40.dp)
            .fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "등록한 장소가 없어요.",
            color = SolplyTheme.colors.gray600,
            style = SolplyTheme.typography.body16R
        )
    }
}
