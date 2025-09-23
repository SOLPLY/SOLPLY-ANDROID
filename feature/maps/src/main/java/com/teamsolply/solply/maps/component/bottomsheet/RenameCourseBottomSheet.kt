package com.teamsolply.solply.maps.component.bottomsheet

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.teamsolply.solply.designsystem.R
import com.teamsolply.solply.designsystem.component.button.SolplyBasicButton
import com.teamsolply.solply.designsystem.component.textfield.SolplyRenameCourseTextField
import com.teamsolply.solply.designsystem.theme.SolplyTheme
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
    onStartEditCourseClick: () -> Unit,
    ) {
    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true
    )

    LaunchedEffect(Unit) {
        sheetState.expand()
    }

    ModalBottomSheet(
        onDismissRequest = onDismissRequest,
        sheetState = sheetState,
        containerColor = SolplyTheme.colors.white,
        dragHandle = null
    ) {
        Column(
            modifier = Modifier
                .height(670.dp)
                .padding(horizontal = 16.dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 17.dp, bottom = 37.dp)
            ) {
                Text(
                    text = "코스 정보 수정",
                    color = SolplyTheme.colors.black,
                    style = SolplyTheme.typography.head16M,
                    modifier = Modifier.align(Alignment.Center)
                )
                Icon(
                    painter = painterResource(R.drawable.ic_close),
                    modifier = Modifier
                        .align(Alignment.CenterEnd)
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
                onValueChange = { renameCourseName(it) },
                modifier = Modifier.padding(bottom = 32.dp)
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
                onValueChange = { renameCourseIntroduction(it) }
            )
            Spacer(modifier = Modifier.weight(1f))
            SolplyBasicButton(
                text = "완료",
                onClick = {
                    onStartRenameCourseClick()
                    onStartEditCourseClick()
                },
                modifier = Modifier
                    .padding(start = 20.dp, end = 20.dp, bottom = 36.dp),
                textPadding = PaddingValues(vertical = 21.dp),
                enabledBackgroundColor = SolplyTheme.colors.gray900
            )
        }
    }
}
