package com.teamsolply.solply.designsystem.component.bottomsheet

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.skydoves.flexible.bottomsheet.material3.FlexibleBottomSheet
import com.skydoves.flexible.core.FlexibleSheetSize
import com.skydoves.flexible.core.rememberFlexibleBottomSheetState
import com.teamsolply.solply.designsystem.theme.SolplyTheme

@SuppressLint("UseOfNonLambdaOffsetOverload", "UnrememberedMutableState")
@Composable
fun SolplyBasicBottomSheet(
    modifier: Modifier = Modifier,
    content: @Composable ColumnScope.() -> Unit
) {
    val sheetState = rememberFlexibleBottomSheetState(
        allowNestedScroll = false,
        skipHiddenState = true,
        isModal = false,
        skipSlightlyExpanded = false,
        containSystemBars = false,
        flexibleSheetSize = FlexibleSheetSize(
            fullyExpanded = 0.86f,
            intermediatelyExpanded = 0.5f,
            slightlyExpanded = 0.14f
        )
    )

    Box(
        modifier = modifier
    ) {
        FlexibleBottomSheet(
            onDismissRequest = {},
            sheetState = sheetState,
            containerColor = SolplyTheme.colors.white,
            windowInsets = WindowInsets(0)
        ) {
            content()
        }
    }
}
