package com.teamsolply.solply.onboarding.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import com.teamsolply.solply.designsystem.theme.SolplyTheme
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource

@Composable
fun MoodOptionBox(
    text: String,
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    shape: Shape = RoundedCornerShape(20.dp),
    selectedBackgroundColor: Color = SolplyTheme.colors.red100,
    unselectedBackgroundColor: Color = SolplyTheme.colors.gray100,
    selectedBorderColor: Color = SolplyTheme.colors.red300,
    unselectedBorderColor: Color = SolplyTheme.colors.gray300,
    textColor: Color = SolplyTheme.colors.gray900,
    textStyle: TextStyle = SolplyTheme.typography.body16M,
    iconResId: Int = com.teamsolply.solply.designsystem.R.drawable.ic_select_icon
) {
    val backgroundColor = if (selected) selectedBackgroundColor else unselectedBackgroundColor
    val borderColor = if (selected) selectedBorderColor else unselectedBorderColor

    Row(
        modifier = modifier
            .clip(shape)
            .background(backgroundColor)
            .border(width = 1.dp, color = borderColor, shape = shape)
            .clickable(
                indication = null,
                interactionSource = remember { MutableInteractionSource() }
            ) { onClick() }
            .padding(horizontal = 16.dp, vertical = 12.dp)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = text,
            color = textColor,
            style = textStyle
        )
        if (selected) {
            Icon(
                painter = painterResource(id = iconResId),
                contentDescription = "selected",
                tint = Color.Unspecified,
                modifier = Modifier
                    .size(24.dp)
            )
        }
    }
}
