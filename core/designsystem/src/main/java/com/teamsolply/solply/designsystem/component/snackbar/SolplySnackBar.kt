package com.teamsolply.solply.designsystem.component.snackbar

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.teamsolply.solply.designsystem.theme.SolplyTheme
import com.teamsolply.solply.ui.extension.customClickable

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
}

@Composable
fun SolplyNavigateSnackBar(text: String, navigateToRoute: () -> Unit) {
    // TODO. 디자인 바꾸기
    SolplySnackBar {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 14.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = text,
                style = SolplyTheme.typography.body14R,
                color = SolplyTheme.colors.white
            )
            Text(
                text = "click",
                style = SolplyTheme.typography.body14R,
                color = SolplyTheme.colors.white,
                modifier = Modifier.customClickable { navigateToRoute() }
            )
        }
    }
}
