package com.teamsolply.solply.maps.component.bottomsheet

import androidx.compose.foundation.focusable
import androidx.compose.foundation.gestures.awaitEachGesture
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.gestures.waitForUpOrCancellation
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.minimumInteractiveComponentSize
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.input.pointer.PointerEventPass
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.teamsolply.solply.designsystem.R
import com.teamsolply.solply.designsystem.component.button.SolplyBasicButton
import com.teamsolply.solply.designsystem.component.textfield.SolplyRenameCourseTextField
import com.teamsolply.solply.designsystem.theme.SolplyTheme
import com.teamsolply.solply.ui.extension.clearFocusOnTapOutside
import com.teamsolply.solply.ui.extension.customClickable

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RenameCourseBottomSheet(
    onDismissRequest: () -> Unit = {},
    newCourseName: String,
    newCourseIntroduction: String,
    renameCourseName: (String) -> Unit,
    renameCourseIntroduction: (String) -> Unit,
    onStartRenameCourseClick: () -> Unit,
    onChangeRenameCourse: (String, String) -> Unit,
) {
    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true
    )

    LaunchedEffect(Unit) {
        sheetState.expand()
    }

    val focusManager = LocalFocusManager.current
    val keyboard = LocalSoftwareKeyboardController.current
    val blankFocus = remember { FocusRequester() }

    val isButtonEnabled = newCourseName.isNotEmpty() &&
            newCourseName.length != 18 &&
            newCourseIntroduction.isNotEmpty() &&
            newCourseIntroduction.length != 20

    ModalBottomSheet(
        onDismissRequest = {
            focusManager.clearFocus(force = true)
            keyboard?.hide()
            onDismissRequest()
        },
        sheetState = sheetState,
        containerColor = SolplyTheme.colors.white,
        dragHandle = null
    ) {
        Column(
            modifier = Modifier
                .height(670.dp)
                .padding(horizontal = 16.dp)
                .focusRequester(blankFocus)
                .focusable()
                .clearFocusOnTapOutside(
                    focusManager = focusManager,
                    keyboard = keyboard
                )
                .pointerInput(Unit) {
                    awaitEachGesture {
                        awaitFirstDown(requireUnconsumed = false, pass = PointerEventPass.Final)
                        blankFocus.requestFocus()
                        keyboard?.hide()
                        waitForUpOrCancellation()
                    }
                }
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 4.dp, top = 17.dp, bottom = 37.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "코스 정보 수정",
                    color = SolplyTheme.colors.black,
                    style = SolplyTheme.typography.head16M,
                )
                Icon(
                    painter = painterResource(R.drawable.ic_close),
                    modifier = Modifier
                        .minimumInteractiveComponentSize()
                        .customClickable(rippleEnabled = false) {
                            onStartRenameCourseClick()
                        },
                    contentDescription = "close_rename_bottomsheet"
                )
            }
            Text(
                text = "코스 이름",
                modifier = Modifier
                    .padding(bottom = 12.dp),
                color = SolplyTheme.colors.gray900,
                style = SolplyTheme.typography.title14M
            )
            SolplyRenameCourseTextField(
                value = newCourseName,
                onValueChange = { newValue ->
                    if (newValue.length <= 18) {
                        renameCourseName(newValue)
                    }
                },
                modifier = Modifier.padding(bottom = 8.dp)
            )
            Text(
                text = "${newCourseName.length}/18",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 24.dp),
                textAlign = TextAlign.End,
                color = SolplyTheme.colors.gray500,
                style = SolplyTheme.typography.caption12M
            )
            Text(
                text = "코스 한 줄 소개",
                modifier = Modifier
                    .padding(bottom = 12.dp),
                color = SolplyTheme.colors.gray900,
                style = SolplyTheme.typography.title14M
            )
            SolplyRenameCourseTextField(
                value = newCourseIntroduction,
                onValueChange = { newValue ->
                    if (newValue.length <= 20) {
                        renameCourseIntroduction(newValue)
                    }
                },
                modifier = Modifier.padding(bottom = 8.dp)
            )
            Text(
                text = "${newCourseIntroduction.length}/20",
                modifier = Modifier
                    .fillMaxWidth(),
                textAlign = TextAlign.End,
                color = SolplyTheme.colors.gray500,
                style = SolplyTheme.typography.caption12M
            )
            Spacer(modifier = Modifier.weight(1f))
            SolplyBasicButton(
                text = "완료",
                onClick = {
                    if (isButtonEnabled) {
                        onChangeRenameCourse(newCourseName, newCourseIntroduction)
                        onStartRenameCourseClick()
                    }
                },
                modifier = Modifier
                    .padding(start = 20.dp, end = 20.dp, bottom = 36.dp),
                selected = isButtonEnabled,
                textPadding = PaddingValues(vertical = 21.dp),
                enabledBackgroundColor = SolplyTheme.colors.gray900
            )
        }
    }
}
