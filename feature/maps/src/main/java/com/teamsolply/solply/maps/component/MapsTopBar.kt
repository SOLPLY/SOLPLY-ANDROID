package com.teamsolply.solply.maps.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.teamsolply.solply.designsystem.theme.SolplyTheme
import com.teamsolply.solply.ui.extension.customClickable

@Composable
fun MapsTopBar(
    title: String,
    onBackClick: () -> Unit,
    onHomeClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            painter = painterResource(com.teamsolply.solply.designsystem.R.drawable.ic_back_arrow),
            contentDescription = "back",
            tint = Color.Unspecified,
            modifier = Modifier
                .align(Alignment.CenterStart)
                .padding(start = 28.dp)
                .customClickable(rippleEnabled = false) { onBackClick() }
        )
        Text(
            text = title,
            style = SolplyTheme.typography.title18Sb,
            color = SolplyTheme.colors.black
        )
        Icon(
            painter = painterResource(com.teamsolply.solply.designsystem.R.drawable.ic_home),
            contentDescription = "home",
            tint = Color.Unspecified,
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .padding(end = 28.dp)
                .customClickable(rippleEnabled = false) { onHomeClick() }
        )
    }
}
