package com.teamsolply.solply.designsystem.component.textfield

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.teamsolply.solply.designsystem.R
import com.teamsolply.solply.designsystem.theme.SolplyTheme

@Composable
private fun BaseTextField(
    value: String,
    isCorrect: Boolean,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    backgroundColor: Color = SolplyTheme.colors.white,
    shape: Shape = RoundedCornerShape(20.dp),
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
    maxLength: Int = 8,
    maxLines: Int = 1
) {
    Box(
        modifier = modifier
            .background(color = backgroundColor, shape = shape)
            .border(
                width = borderWidth.dp,
                color = borderColor ?: Color.Transparent,
                shape = shape
            ),
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
            if (value.isNotEmpty() && value.length < maxLength) {
                val iconRes =
                    if (isCorrect) R.drawable.ic_textfield_correct else R.drawable.ic_textfield_wrong
                val contentDescription = if (isCorrect) "textfield_correct" else "textfield_wrong"
                Icon(
                    painter = painterResource(iconRes),
                    contentDescription = contentDescription,
                    tint = Color.Unspecified
                )
            } else {
                Box(modifier = Modifier.size(24.dp))
            }
        }
    }
}

@Composable
fun SolplyNicknameTextField(
    value: String,
    isNicknameDuplicate: Boolean,
    onValueChange: (String) -> Unit,
    changeNicknameValidate: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
    placeholder: String = "여기에 입력하세요.",
    maxLength: Int = 8,
    minLength: Int = 2,
    checkNicknameValidate: (String) -> Boolean
) {
    var validationState by remember { mutableStateOf<NickNameValidateState>(NickNameValidateState.Empty) }
    var isTyping by remember { mutableStateOf(false) }

    val isCorrect = validationState in listOf(
        NickNameValidateState.Valid,
        NickNameValidateState.MaxLength,
        NickNameValidateState.Typing
    ) ||
        (validationState == NickNameValidateState.Empty && value.isNotEmpty())

    LaunchedEffect(value, isNicknameDuplicate) {
        if (value.isNotEmpty()) {
            isTyping = true
            if (value.length < minLength) {
                validationState = NickNameValidateState.TooShort
                changeNicknameValidate(false)
                isTyping = false
                return@LaunchedEffect
            }
            if (value.length == maxLength) {
                validationState = NickNameValidateState.MaxLength
                changeNicknameValidate(false)
                isTyping = false
                return@LaunchedEffect
            }

            val hasInvalidChars = !checkNicknameValidate(value)
            if (hasInvalidChars) {
                validationState = NickNameValidateState.Invalid
                changeNicknameValidate(false)
                isTyping = false
            } else {
                if (isNicknameDuplicate) {
                    validationState = NickNameValidateState.Duplicate
                    changeNicknameValidate(false)
                } else {
                    validationState = NickNameValidateState.Valid
                    changeNicknameValidate(true)
                }
                isTyping = false
            }
        } else {
            validationState = NickNameValidateState.Empty
            changeNicknameValidate(false)
            isTyping = false
        }
    }

    val (backgroundColor, borderColor) = when {
        value.isEmpty() -> Pair(SolplyTheme.colors.white, SolplyTheme.colors.gray300)
        value.length == maxLength -> Pair(SolplyTheme.colors.white, SolplyTheme.colors.gray900)
        validationState in listOf(
            NickNameValidateState.Duplicate,
            NickNameValidateState.Invalid,
            NickNameValidateState.TooShort
        ) ->
            Pair(SolplyTheme.colors.white, SolplyTheme.colors.red600)

        validationState == NickNameValidateState.Typing ->
            Pair(SolplyTheme.colors.green200, SolplyTheme.colors.green500)

        else -> Pair(SolplyTheme.colors.green200, SolplyTheme.colors.green500)
    }

    Column(modifier = modifier) {
        BaseTextField(
            value = value,
            isCorrect = isCorrect,
            onValueChange = { newValue ->
                if (newValue.length <= maxLength) {
                    onValueChange(newValue)
                }
            },
            modifier = Modifier.padding(bottom = 8.dp),
            backgroundColor = backgroundColor,
            borderColor = borderColor,
            borderWidth = 1f,
            placeholder = placeholder
        )

        ValidationMessageRow(
            validationState = if (isTyping) NickNameValidateState.Typing else validationState,
            valueLength = value.length,
            maxLength = maxLength,
            borderColor = borderColor
        )
    }
}

@Composable
private fun ValidationMessageRow(
    validationState: NickNameValidateState,
    valueLength: Int,
    maxLength: Int,
    borderColor: Color
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        val messageData = when (validationState) {
            NickNameValidateState.Duplicate -> ValidationMessage(
                text = "중복된 이름이에요.",
                color = SolplyTheme.colors.red600
            )

            NickNameValidateState.Invalid -> ValidationMessage(
                text = "사용할 수 없는 문자가 있어요.",
                color = SolplyTheme.colors.red600
            )

            NickNameValidateState.Valid -> ValidationMessage(
                text = "사용 가능한 이름이에요.",
                color = SolplyTheme.colors.green500
            )

            else -> null
        }

        messageData?.let { message ->
            Text(
                text = message.text,
                modifier = Modifier.padding(end = 40.dp),
                style = SolplyTheme.typography.caption12R,
                color = message.color
            )
        } ?: Spacer(modifier = Modifier)

        Text(
            text = "$valueLength/$maxLength",
            style = SolplyTheme.typography.caption12R,
            color = borderColor
        )
    }
}

private data class ValidationMessage(
    val text: String,
    val color: Color
)

sealed class NickNameValidateState {
    data object Empty : NickNameValidateState()
    data object MaxLength : NickNameValidateState()
    data object Invalid : NickNameValidateState()
    data object Duplicate : NickNameValidateState()
    data object Valid : NickNameValidateState()
    data object Typing : NickNameValidateState()
    data object TooShort : NickNameValidateState()
}
