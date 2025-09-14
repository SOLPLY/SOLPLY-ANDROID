package com.teamsolply.solply.designsystem.component.bottomsheet

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import com.skydoves.flexible.bottomsheet.material3.FlexibleBottomSheet
import com.skydoves.flexible.core.FlexibleSheetSize
import com.skydoves.flexible.core.rememberFlexibleBottomSheetState
import com.teamsolply.solply.designsystem.theme.SolplyTheme
import kotlinx.coroutines.flow.distinctUntilChanged

@SuppressLint("UseOfNonLambdaOffsetOverload", "UnrememberedMutableState")
@Composable
fun SolplyBasicBottomSheet(
    modifier: Modifier = Modifier,
    menuContent: @Composable RowScope.() -> Unit,
    content: @Composable ColumnScope.() -> Unit
) {
    val configuration = LocalConfiguration.current
    val density = LocalDensity.current

    val sheetState = rememberFlexibleBottomSheetState(
        allowNestedScroll = false,
        skipHiddenState = true,
        isModal = false,
        skipSlightlyExpanded = false,
        containSystemBars = false,
        flexibleSheetSize = FlexibleSheetSize(
            fullyExpanded = 0.93f,
            intermediatelyExpanded = 0.6f,
            slightlyExpanded = 0.13f
        )
    )

    var currentOffset by remember { mutableFloatStateOf(0f) }
    val currentValue = sheetState.currentValue

    LaunchedEffect(sheetState) {
        snapshotFlow {
            try {
                sheetState.requireOffset()
            } catch (e: IllegalStateException) {
                0f
            }
        }
            .distinctUntilChanged()
            .collect { offset ->
                currentOffset = offset
            }
    }

    val bottomSheetHeight by remember(currentOffset) {
        derivedStateOf {
            val screenHeight = configuration.screenHeightDp.dp
            val offsetDp = with(density) { currentOffset.toDp() }
            screenHeight - offsetDp - 40.dp
        }
    }

    val isMenuVisible by remember(currentValue, currentOffset) {
        derivedStateOf {
            when (currentValue) {
                com.skydoves.flexible.core.FlexibleSheetValue.SlightlyExpanded -> true
                com.skydoves.flexible.core.FlexibleSheetValue.IntermediatelyExpanded -> {
                    val screenHeightPx = configuration.screenHeightDp * density.density
                    val expansionRatio = 1f - (currentOffset / screenHeightPx)
                    expansionRatio < 0.7f
                }

                com.skydoves.flexible.core.FlexibleSheetValue.FullyExpanded -> false
                com.skydoves.flexible.core.FlexibleSheetValue.Hidden -> true
            }
        }
    }

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

        if (isMenuVisible) {
            Row(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .offset(y = -bottomSheetHeight)
                    .padding(end = 16.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                menuContent()
            }
        }
    }
}
