package com.teamsolply.solply.place.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.teamsolply.solply.designsystem.theme.SolplyTheme

@Composable
fun PlaceChipButton(
    text: String,
    modifier: Modifier = Modifier,
    shape: RoundedCornerShape = RoundedCornerShape(20.dp),
    backgroundColor: Color = SolplyTheme.colors.white,
    borderColor: Color = SolplyTheme.colors.gray300,
    contentColor: Color = SolplyTheme.colors.black,
    onClick: () -> Unit = {}
) {
    Row(
        modifier = modifier
            .border(width = 1.dp, color = borderColor, shape = shape)
            .background(color = backgroundColor, shape = shape)
            .clickable(onClick = onClick)
            .padding(start = 12.dp, top = 4.dp, bottom = 4.dp, end = 4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = text,
            style = SolplyTheme.typography.body14R,
            color = contentColor
        )
        Icon(
            painter = painterResource(id = com.teamsolply.solply.designsystem.R.drawable.ic_under_arrow),
            contentDescription = null,
            tint = contentColor
        )
    }
}
