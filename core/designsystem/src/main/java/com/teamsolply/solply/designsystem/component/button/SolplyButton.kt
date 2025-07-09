package com.teamsolply.solply.designsystem.component.button

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
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
    val baseModifier = modifier
        .background(color = backgroundColor, shape = shape)
        .customClickable(rippleEnabled = false) { onClick() }

    val finalModifier = if (borderColor != null) {
        baseModifier.border(
            width = borderWidth.dp,
            color = borderColor,
            shape = shape
        )
    } else {
        baseModifier
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
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    isShowMore: Boolean = false,
    selected: Boolean = false
) {
    val backgroundColor = when {
        isShowMore -> SolplyTheme.colors.gray200
        selected -> SolplyTheme.colors.gray400
        else -> SolplyTheme.colors.gray200
    }

    BaseButton(
        onClick = onClick,
        modifier = modifier.size(74.dp),
        backgroundColor = backgroundColor
    ) {
        if (isShowMore) {
            Icon(
                painter = painterResource(R.drawable.ic_cross),
                contentDescription = "show_more",
                tint = Color.Unspecified
            )
        } else {
            Text(
                text = text,
                style = SolplyTheme.typography.body16R,
                color = SolplyTheme.colors.black
            )
        }
    }
}

@Composable
fun OnBoardingSkipButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    BaseButton(
        onClick = onClick,
        modifier = modifier,
        backgroundColor = SolplyTheme.colors.white,
        borderColor = SolplyTheme.colors.gray500,
        borderWidth = 1f
    ) {
        Text(
            text = "건너뛰기",
            modifier = Modifier.padding(horizontal = 20.dp, vertical = 24.dp),
            style = SolplyTheme.typography.body14M,
            color = SolplyTheme.colors.gray700
        )
    }
}

@Composable
fun AddPlaceButton(
    onClick: () -> Unit,
    selected: Boolean,
    modifier: Modifier = Modifier
) {
    val backgroundColor = if (selected) { SolplyTheme.colors.purple400 } else { SolplyTheme.colors.white }

    val textColor = if (selected) SolplyTheme.colors.purple800 else SolplyTheme.colors.gray400
    val iconColor = if (selected) SolplyTheme.colors.purple700 else SolplyTheme.colors.gray400

    BaseButton(
        onClick = onClick,
        modifier = modifier
            .height(49.dp),
        backgroundColor = backgroundColor,
        shape = RoundedCornerShape(26.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier
                .padding(horizontal = 12.dp)
        ) {
            Text(
                text = "내 코스에 추가",
                style = SolplyTheme.typography.body14M,
                color = textColor,
                maxLines = 1
            )
        }
    }
}

@Composable
fun AddCourseButton(
    onClick: () -> Unit,
    selected: Boolean,
    modifier: Modifier = Modifier
) {
    val text = if (selected) "저장된 코스" else "코스 저장"
    val backgroundColor = if (selected) SolplyTheme.colors.red100 else SolplyTheme.colors.white
    val textColor = if (selected) SolplyTheme.colors.red500 else SolplyTheme.colors.purple600
    val iconColor = if (selected) SolplyTheme.colors.red500 else SolplyTheme.colors.purple600
    val paddingValues = if (selected) PaddingValues(end = 8.dp) else PaddingValues(end = 12.dp)

    BaseButton(
        onClick = onClick,
        modifier = modifier.width(126.dp),
        backgroundColor = backgroundColor,
        shape = RoundedCornerShape(26.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.End,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 12.dp)
        ) {
            Text(
                text = text,
                modifier = Modifier.padding(paddingValues),
                style = SolplyTheme.typography.button14M,
                color = textColor
            )
            Icon(
                painter = painterResource(R.drawable.ic_marker_default),
                modifier = Modifier.padding(end = 12.dp),
                contentDescription = "add_course",
                tint = iconColor
            )
        }
    }
}
