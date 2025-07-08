package com.teamsolply.solply.place.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.teamsolply.solply.designsystem.theme.SolplyTheme

@Composable
fun FilterChipButton(
    text: String,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val backgroundColor = if (isSelected) SolplyTheme.colors.gray900 else SolplyTheme.colors.white
    val contentColor = if (isSelected) SolplyTheme.colors.white else SolplyTheme.colors.gray900
    val shape = RoundedCornerShape(99.dp)

    val chipMod = modifier
        .height(40.dp)
        .background(backgroundColor, shape)
        .clickable(onClick = onClick)
        .then(if (!isSelected) Modifier.border(1.dp, SolplyTheme.colors.gray300, shape) else Modifier)
        .padding(horizontal = 16.dp, vertical = 8.dp)
    
    Row(
        modifier = chipMod,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = text, style = SolplyTheme.typography.body16M, color = contentColor)
    }
}
