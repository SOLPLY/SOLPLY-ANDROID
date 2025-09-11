package com.teamsolply.solply.designsystem.component.bottomsheet

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.skydoves.flexible.bottomsheet.material3.FlexibleBottomSheet
import com.skydoves.flexible.core.FlexibleSheetSize
import com.skydoves.flexible.core.rememberFlexibleBottomSheetState
import com.teamsolply.solply.designsystem.theme.SolplyTheme

@SuppressLint("UseOfNonLambdaOffsetOverload")
@Composable
fun SolplyBasicBottomSheet(
    modifier: Modifier = Modifier,
    menuContent: @Composable RowScope.() -> Unit,
    content: @Composable ColumnScope.() -> Unit
) {
    val sheetState = rememberFlexibleBottomSheetState(
        skipHiddenState = true,
        isModal = false,
        skipSlightlyExpanded = false,
        containSystemBars = false,
        flexibleSheetSize = FlexibleSheetSize(
            fullyExpanded = 0.9f,
            intermediatelyExpanded = 0.6f,
            slightlyExpanded = 0.25f
        )
    )

    FlexibleBottomSheet(
        onDismissRequest = {},
        sheetState = sheetState,
        containerColor = SolplyTheme.colors.white,
        windowInsets = WindowInsets(0),
    ) {
        content()
    }
}
