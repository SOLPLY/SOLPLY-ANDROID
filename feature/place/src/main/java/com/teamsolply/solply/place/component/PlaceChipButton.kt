package com.teamsolply.solply.place.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.teamsolply.solply.designsystem.R
import com.teamsolply.solply.designsystem.theme.SolplyTheme

@Composable
fun PlaceChipButton(
    text: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {}
) {
    val backgroundColor = SolplyTheme.colors.white
    val borderColor = SolplyTheme.colors.gray300
    val contentColor = SolplyTheme.colors.black

    Row(
        modifier
            .border(1.dp, borderColor, CircleShape)
            .background(backgroundColor, CircleShape)
            .clickable(onClick = onClick)
            .padding(start = 12.dp, end = 4.dp, top = 4.dp, bottom = 4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text, style = SolplyTheme.typography.body14R, color = contentColor)
        Icon(
            painter = painterResource(R.drawable.ic_under_arrow),
            contentDescription = null,
            tint = contentColor
        )
    }
}
