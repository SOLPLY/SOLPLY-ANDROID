package com.teamsolply.solply.maps.component.dialog

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.teamsolply.solply.designsystem.component.button.SolplyBasicButton
import com.teamsolply.solply.designsystem.theme.SolplyTheme

@Composable
fun CourseSaveDialog(
    saveToCourseClick: () -> Unit,
    saveToNewCourseClick: () -> Unit,
    onDismissRequest: () -> Unit
) {
    Dialog(
        onDismissRequest = onDismissRequest
    ) {
        Column(
            modifier = Modifier
                .padding(bottom = 24.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.Bottom
        ) {
            SolplyBasicButton(
                text = "지금 코스에 저장",
                modifier = Modifier.padding(bottom = 12.dp),
                onClick = saveToCourseClick,
                textColor = SolplyTheme.colors.gray900,
                textPadding = PaddingValues(vertical = 21.dp),
                enabledBackgroundColor = SolplyTheme.colors.white
            )
            SolplyBasicButton(
                text = "새 코스로 저장",
                onClick = saveToNewCourseClick,
                textColor = SolplyTheme.colors.gray900,
                textPadding = PaddingValues(vertical = 21.dp),
                enabledBackgroundColor = SolplyTheme.colors.white
            )
        }
    }
}
