package com.teamsolply.solply.designsystem.component.textfield

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.teamsolply.solply.designsystem.theme.SolplyTheme
import kotlinx.coroutines.delay

@Composable
private fun BaseTextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    backgroundColor: Color = SolplyTheme.colors.white,
    shape: Shape = RoundedCornerShape(12.dp),
    borderColor: Color? = null,
    borderWidth: Float = 0f,
    textColor: Color = SolplyTheme.colors.gray900,
    textStyle: TextStyle = SolplyTheme.typography.body16R,
    placeholder: String? = null,
    placeholderColor: Color = SolplyTheme.colors.gray500,
    placeholderStyle: TextStyle = SolplyTheme.typography.body16R,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    singleLine: Boolean = true,
    maxLines: Int = 1,
    trailingContent: @Composable (() -> Unit)? = null,
) {
    val finalModifier = modifier
        .background(color = backgroundColor, shape = shape)
        .let { baseModifier ->
            if (borderColor != null) {
                baseModifier.border(
                    width = borderWidth.dp,
                    color = borderColor,
                    shape = shape
                )
            } else {
                baseModifier
            }
        }

    Box(
        modifier = finalModifier,
        contentAlignment = Alignment.CenterStart
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier.weight(1f),
                contentAlignment = Alignment.CenterStart
            ) {
                if (value.isEmpty() && placeholder != null) {
                    Text(
                        text = placeholder,
                        style = placeholderStyle,
                        color = placeholderColor
                    )
                }

                BasicTextField(
                    value = value,
                    onValueChange = onValueChange,
                    textStyle = textStyle.copy(color = textColor),
                    cursorBrush = SolidColor(SolplyTheme.colors.black),
                    keyboardOptions = keyboardOptions,
                    visualTransformation = visualTransformation,
                    singleLine = singleLine,
                    maxLines = maxLines,
                    modifier = Modifier.fillMaxWidth()
                )
            }
            trailingContent?.invoke()
        }
    }
}

@Composable
fun SolplyNicknameTextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    placeholder: String = "여기에 입력하세요.",
    maxLength: Int = 8,
    onDuplicateCheck: (String) -> Boolean,
) {
    var isDuplicate by remember { mutableStateOf(false) }

    LaunchedEffect(value) {
        if (value.isNotEmpty()) {
            delay(300)
            isDuplicate = onDuplicateCheck(value)
        } else {
            isDuplicate = false
        }
    }

    val (backgroundColor, borderColor, countColor) =
        when {
            value.isEmpty() -> Triple(
                SolplyTheme.colors.white,
                SolplyTheme.colors.gray300,
                SolplyTheme.colors.gray500
            )

            value.length == maxLength -> Triple(
                SolplyTheme.colors.white,
                SolplyTheme.colors.gray900,
                SolplyTheme.colors.gray700
            )

            isDuplicate -> Triple(
                SolplyTheme.colors.white,
                SolplyTheme.colors.red600,
                SolplyTheme.colors.red600
            )


            else -> Triple(
                SolplyTheme.colors.green200,
                SolplyTheme.colors.green500,
                SolplyTheme.colors.gray500
            )
        }
    
    Box(modifier = modifier) {
        BaseTextField(
            value = value,
            onValueChange = { newValue ->
                if (newValue.length <= maxLength) {
                    onValueChange(newValue)
                }
            },
            modifier = Modifier,
            backgroundColor = backgroundColor,
            borderColor = borderColor,
            borderWidth = 1f,
            placeholder = placeholder,
            trailingContent = {
                Text(
                    text = "${value.length}/$maxLength",
                    style = SolplyTheme.typography.caption12R,
                    color = countColor
                )
            }
        )
        if (isDuplicate) {
            Text(
                text = "중복된 이름이에요.",
                modifier = Modifier
                    .align(Alignment.CenterEnd)
                    .padding(end = 40.dp),
                style = SolplyTheme.typography.caption12R,
                color = SolplyTheme.colors.red600
            )
        }
    }
}