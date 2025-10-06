package com.teamsolply.solply.registerplace

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.teamsolply.solply.designsystem.R
import com.teamsolply.solply.designsystem.component.textfield.SolplyRenameCourseTextField
import com.teamsolply.solply.designsystem.theme.SolplyTheme
import com.teamsolply.solply.ui.extension.addFocusCleaner
import com.teamsolply.solply.ui.extension.customClickable

@Composable
fun RegisterPlaceRoute(
    paddingValues: PaddingValues,
    navigateToBack: () -> Unit,
    viewModel: RegisterPlaceViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    RegisterPlaceScreen(
        uiState = uiState,
        paddingValues = paddingValues,
        navigateToBack = navigateToBack,
        inputPlaceNameText = { text ->
            viewModel.sendIntent(
                RegisterPlaceIntent.InputPlaceNameText(
                    text
                )
            )
        }
    )
}

@Composable
fun RegisterPlaceScreen(
    uiState: RegisterPlaceState,
    paddingValues: PaddingValues,
    navigateToBack: () -> Unit,
    inputPlaceNameText: (String) -> Unit,
) {
    val focusManager = LocalFocusManager.current
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
            .background(color = SolplyTheme.colors.white)
            .addFocusCleaner(focusManager)
    ) {
        Row(
            modifier = Modifier
                .padding(start = 16.dp, top = 16.dp, bottom = 32.dp)
                .customClickable(rippleEnabled = false) {
                    navigateToBack()
                }
        ) {
            Icon(
                painter = painterResource(R.drawable.ic_back_long_arrow),
                modifier = Modifier.padding(end = 12.dp),
                contentDescription = "back_arrow",
                tint = SolplyTheme.colors.gray900
            )
            Text(
                text = "장소 등록하기",
                color = SolplyTheme.colors.black,
                style = SolplyTheme.typography.head16M
            )
        }
        Column(modifier = Modifier.padding(horizontal = 16.dp)) {
            Text(
                text = "장소 이름",
                modifier = Modifier.padding(bottom = 12.dp),
                color = SolplyTheme.colors.black,
                style = SolplyTheme.typography.body16M
            )
            SolplyRenameCourseTextField(
                value = uiState.placeName,
                onValueChange = inputPlaceNameText,
                placeholder = "장소 이름을 입력하세요",
                innerPadding = PaddingValues(horizontal = 20.dp, vertical = 14.dp),
                trailingIcon = {
                    Text(
                        text = "직접 등록",
                        style = SolplyTheme.typography.body14R.copy(
                            textDecoration = TextDecoration.Underline
                        ),
                        color = SolplyTheme.colors.purple600
                    )
                }
            )
        }
    }
}