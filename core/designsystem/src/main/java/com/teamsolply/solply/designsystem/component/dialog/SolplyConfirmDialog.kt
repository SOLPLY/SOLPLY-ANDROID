package com.teamsolply.solply.designsystem.component.dialog

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.teamsolply.solply.designsystem.component.button.SolplyBasicButton
import com.teamsolply.solply.designsystem.theme.SolplyTheme
import com.teamsolply.solply.ui.preview.DefaultPreview

@Composable
fun SolplyConfirmDialog(
    text: String,
    confirmButtonText: String,
    dismissButtonText: String,
    onClickConfirm: () -> Unit,
    onClickDismiss: () -> Unit,
    modifier: Modifier = Modifier
) {
    Dialog(
        onDismissRequest = onClickDismiss,
        properties = DialogProperties(
            dismissOnClickOutside = true,
            dismissOnBackPress = true
        )
    ) {
        Surface(
            modifier = modifier
                .height(146.dp)
                .width(260.dp)
                .clip(shape = RoundedCornerShape(12.dp))
        ) {
            Column(
                modifier = Modifier
                    .padding(12.dp)
                    .fillMaxSize(),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(8f),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = text,
                        style = SolplyTheme.typography.button14R,
                        color = SolplyTheme.colors.black
                    )
                }
                Spacer(modifier = Modifier.weight(1f))
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    SolplyBasicButton(
                        onClick = onClickDismiss,
                        text = dismissButtonText,
                        textStyle = SolplyTheme.typography.button14M,
                        textColor = SolplyTheme.colors.black,
                        enabledBackgroundColor = SolplyTheme.colors.white,
                        textPadding = PaddingValues(vertical = 14.dp, horizontal = 46.dp),
                        modifier = Modifier.weight(1f)
                    )
                    SolplyBasicButton(
                        onClick = onClickConfirm,
                        text = confirmButtonText,
                        textStyle = SolplyTheme.typography.button14M,
                        textColor = SolplyTheme.colors.white,
                        enabledBackgroundColor = SolplyTheme.colors.black,
                        textPadding = PaddingValues(vertical = 14.dp, horizontal = 46.dp),
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        }
    }
}

@DefaultPreview
@Composable
private fun SolplyConfirmDialogPreview() {
    SolplyTheme {
        SolplyConfirmDialog(
            text = "선택한 장소를 삭제할까요?",
            confirmButtonText = "삭제",
            dismissButtonText = "취소",
            onClickConfirm = {},
            onClickDismiss = {}
        )
    }
}
