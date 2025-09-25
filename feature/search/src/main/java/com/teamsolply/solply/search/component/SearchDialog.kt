package com.teamsolply.solply.search.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.teamsolply.solply.designsystem.theme.SolplyTheme

@Composable
fun SearchDialog(
    onDismissRequest: () -> Unit,
    navigateToPlaceDetail: () -> Unit,
    navigateToBack: () -> Unit,
) {

    Dialog(
        onDismissRequest = onDismissRequest,
        properties = DialogProperties(
            dismissOnBackPress = true,
            dismissOnClickOutside = false,
            usePlatformDefaultWidth = false,
            decorFitsSystemWindows = false
        )
    ) {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = SolplyTheme.colors.white,
            shadowElevation = 32.dp
        ) {
            Column(modifier = Modifier.fillMaxSize()) {
                Text("asd")
            }
        }
    }
}