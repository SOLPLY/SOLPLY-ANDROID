package com.teamsolply.solply.designsystem.component.topbar

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.teamsolply.solply.designsystem.theme.SolplyTheme
import com.teamsolply.solply.ui.extension.customClickable

@Composable
fun SolplyTopBar(
    barText: String,
    onBackButtonClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(SolplyTheme.colors.white),
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(com.teamsolply.solply.designsystem.R.drawable.ic_back),
            contentDescription = "back",
            tint = Color.Unspecified,
            modifier = Modifier
                .padding(start = 16.dp)
                .customClickable(rippleEnabled = false) { onBackButtonClick() }
        )
        Spacer(
            modifier = Modifier
                .width(Dp(12f))
        )
        Text(
            text = barText,
            style = SolplyTheme.typography.title18Sb,
            color = SolplyTheme.colors.black,
            modifier = Modifier.padding(vertical = 18.dp)
        )
    }
}
