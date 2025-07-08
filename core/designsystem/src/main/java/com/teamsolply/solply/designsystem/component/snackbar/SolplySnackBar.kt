package com.teamsolply.solply.designsystem.component.snackbar

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.teamsolply.solply.designsystem.R
import com.teamsolply.solply.designsystem.theme.SolplyTheme
import com.teamsolply.solply.ui.extension.customClickable
import formatTextWithQuotes

@Composable
fun SolplySnackBar(
    content: @Composable () -> Unit
) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(12.dp))
            .background(color = SolplyTheme.colors.black)
    ) {
        content()
    }
}

@Composable
fun SolplyTextSnackBar(text: String) {
    SolplySnackBar {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 14.dp),
            contentAlignment = Alignment.CenterStart
        ) {
            Text(
                text = text,
                style = SolplyTheme.typography.body14R,
                color = SolplyTheme.colors.white
            )
        }
    }
}

@Composable
fun SolplyNotificationSnackBar(text: String) {
    SolplySnackBar {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 14.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Icon(
                painter = painterResource(R.drawable.ic_snackbar_notification),
                contentDescription = "snackbar_notification",
                modifier = Modifier.padding(end = 10.dp),
                tint = Color.Unspecified
            )
            Text(
                text = text,
                style = SolplyTheme.typography.body14R,
                color = SolplyTheme.colors.white
            )
        }
    }
}

@Composable
fun SolplyNavigateSnackBar(text: String, navigateToRoute: () -> Unit) {
    val newText = text.formatTextWithQuotes()
    SolplySnackBar {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 14.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "${newText}에 추가되었어요.",
                style = SolplyTheme.typography.body14R,
                color = SolplyTheme.colors.white
            )
            Row(
                modifier = Modifier.customClickable { navigateToRoute() },
                verticalAlignment = Alignment.CenterVertically,
                ) {
                Text(
                    text = "자세히 보기",
                    style = SolplyTheme.typography.body14R,
                    color = SolplyTheme.colors.purple400,
                )
                Icon(
                    painter = painterResource(R.drawable.ic_next_arrow),
                    contentDescription = "next_arrow",
                    tint = SolplyTheme.colors.purple200
                )
            }
        }
    }
}
