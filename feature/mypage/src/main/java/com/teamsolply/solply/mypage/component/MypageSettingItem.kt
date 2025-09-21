package com.teamsolply.solply.mypage.component

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.teamsolply.solply.designsystem.theme.SolplyTheme

@Composable
fun MypageSettingItem(
    text: String,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 20.dp, end = 16.dp)
    ) {
        Text(
            text = text,
            color = SolplyTheme.colors.black,
            style = SolplyTheme.typography.body16R,
            modifier = Modifier.padding(vertical = 11.dp)
        )
    }
}