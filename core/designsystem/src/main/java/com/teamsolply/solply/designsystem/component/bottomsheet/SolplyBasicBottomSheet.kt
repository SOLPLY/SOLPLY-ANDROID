package com.teamsolply.solply.designsystem.component.bottomsheet

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
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
            slightlyExpanded = 0.12f
        )
    )

    Box(
        modifier = modifier.zIndex(1f)
    ) {
        FlexibleBottomSheet(
            onDismissRequest = {},
            sheetState = sheetState,
            containerColor = SolplyTheme.colors.white,
            windowInsets = WindowInsets(0, 0, 0, 0),
            dragHandle = {
                Box(
                    modifier = Modifier
                        .padding(top = 10.dp, bottom = 20.dp)
                        .size(width = 30.dp, height = 5.dp)
                        .background(
                            shape = RoundedCornerShape(16.dp),
                            color = SolplyTheme.colors.gray300
                        )
                )
            }
        ) {
            content()
        }
    }
}
