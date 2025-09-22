package com.teamsolply.solply.mypage.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.unit.dp
import com.teamsolply.solply.designsystem.theme.SolplyTheme

@Composable
fun MypageSettingItem(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    isBorderEnabled: Boolean = true,
    info: String = ""
) {
    val borderColor = SolplyTheme.colors.gray200
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(start = 20.dp, end = 16.dp)
            .then(
                if (isBorderEnabled) {
                    Modifier.drawBehind {
                        val strokeWidth = 1.dp.toPx()
                        val y = size.height - strokeWidth / 2
                        drawLine(
                            color = borderColor,
                            start = Offset(0f, y),
                            end = Offset(size.width, y),
                            strokeWidth = strokeWidth
                        )
                    }
                } else {
                    Modifier
                }
            ),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = if (info.isNotEmpty()) {
            Arrangement.SpaceBetween
        } else {
            Arrangement.Start
        }
    ) {
        Text(
            text = text,
            color = SolplyTheme.colors.black,
            style = SolplyTheme.typography.body16R,
            modifier = Modifier.padding(vertical = 11.dp)
        )
        if (info.isNotEmpty()) {
            Text(
                text = info,
                color = SolplyTheme.colors.gray600,
                style = SolplyTheme.typography.body16R
            )
        }
    }
}