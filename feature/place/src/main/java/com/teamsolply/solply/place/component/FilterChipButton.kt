package com.teamsolply.solply.place.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.teamsolply.solply.designsystem.theme.SolplyTheme
import com.teamsolply.solply.ui.extension.customClickable

@Composable
fun FilterChipButton(
    text: String,
    isSelected: Boolean = false,
    onClick: () -> Unit
) {
    val backgroundColor = if (isSelected) SolplyTheme.colors.gray900 else SolplyTheme.colors.white
    val contentColor = if (isSelected) SolplyTheme.colors.white else SolplyTheme.colors.gray900

    Box(
        modifier = Modifier
            .then(if (!isSelected) Modifier.border(1.dp, SolplyTheme.colors.gray300, CircleShape) else Modifier)
            .background(backgroundColor, CircleShape)
            .customClickable(rippleEnabled = false) { onClick() }
            .padding(horizontal = 16.dp, vertical = 8.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text,
            style = SolplyTheme.typography.body16M,
            color = contentColor
        )
    }
}