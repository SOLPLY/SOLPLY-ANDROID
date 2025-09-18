package com.teamsolply.solply.onboarding.component

import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.runtime.Composable
import com.teamsolply.solply.designsystem.theme.SolplyTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OnBoardingTownBottomSheet(
    sheetState: SheetState,
    onDismissRequest: () -> Unit = {},
    content: @Composable ColumnScope.() -> Unit,
) {
    ModalBottomSheet(
        onDismissRequest = onDismissRequest,
        sheetState = sheetState,
        containerColor = SolplyTheme.colors.white,
        dragHandle = null,
    ) {
        content()
    }
}