package com.teamsolply.solply.designsystem.component.button

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import com.teamsolply.solply.designsystem.R
import com.teamsolply.solply.designsystem.theme.SolplyTheme
import com.teamsolply.solply.ui.extension.customClickable

@Composable
private fun BaseButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    backgroundColor: Color,
    shape: Shape = CircleShape,
    borderColor: Color? = null,
    borderWidth: Float = 0f,
    content: @Composable () -> Unit
) {
    var finalModifier = modifier
        .then(
            Modifier
                .background(color = backgroundColor, shape = shape)
                .customClickable(rippleEnabled = false) { onClick() }
        )

    if (borderColor != null) {
        finalModifier = finalModifier.border(
            width = borderWidth.dp,
            color = borderColor,
            shape = shape
        )
    }

    Box(
        modifier = finalModifier,
        contentAlignment = Alignment.Center
    ) {
        content()
    }
}

@Composable
fun SolplyBasicButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    selected: Boolean = true,
    textColor: Color = SolplyTheme.colors.white,
    textStyle: TextStyle = SolplyTheme.typography.button16M,
    textPadding: PaddingValues = PaddingValues(vertical = 24.dp),
    enabledBackgroundColor: Color = SolplyTheme.colors.gray700,
    disabledBackgroundColor: Color = SolplyTheme.colors.gray500
) {
    val backgroundColor = if (selected) enabledBackgroundColor else disabledBackgroundColor

    BaseButton(
        onClick = onClick,
        modifier = modifier.fillMaxWidth(),
        backgroundColor = backgroundColor
    ) {
        Text(
            text = text,
            modifier = Modifier.padding(textPadding),
            style = textStyle,
            color = textColor
        )
    }
}

@Composable
fun AddLocalAreaButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    text: String? = null,
    selected: Boolean = false
) {
    val backgroundColor = when {
        selected -> SolplyTheme.colors.red100
        else -> SolplyTheme.colors.gray200
    }
    val borderColor = when {
        selected -> SolplyTheme.colors.red300
        else -> Color.Unspecified
    }

    BaseButton(
        onClick = onClick,
        modifier = modifier.fillMaxWidth(),
        backgroundColor = backgroundColor,
        borderColor = borderColor,
        borderWidth = 1f
    ) {
        if (text.isNullOrEmpty()) {
            Icon(
                painter = painterResource(R.drawable.ic_cross),
                contentDescription = "show_more",
                modifier = modifier.padding(vertical = 14.dp),
                tint = Color.Unspecified
            )
        } else {
            Text(
                text = text,
                modifier = modifier.padding(vertical = 14.dp),
                style = SolplyTheme.typography.body16R,
                color = SolplyTheme.colors.gray900
            )
        }
    }
}
