package com.teamsolply.solply.designsystem.component.bottomsheet

import android.annotation.SuppressLint
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import com.teamsolply.solply.designsystem.theme.SolplyTheme
import com.teamsolply.solply.model.BottomSheetState
import kotlin.math.abs

@SuppressLint("UseOfNonLambdaOffsetOverload")
@Composable
fun SolplyBasicBottomSheet(
    modifier: Modifier = Modifier,
    menuContent: @Composable RowScope.() -> Unit,
    content: @Composable ColumnScope.() -> Unit
) {
    var currentState by remember { mutableStateOf(BottomSheetState.HALF_EXPANDED) }
    var dragOffset by remember { mutableFloatStateOf(0f) }
    val density = LocalDensity.current
    val collapsedHeight = with(density) { 0.dp.toPx() }
    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp.dp
    val halfExpandedHeight = with(density) { (screenHeight * 0.35f).toPx() }
    val expandedHeight = with(density) { (screenHeight * 0.76f).toPx() }
    val currentHeight = when (currentState) {
        BottomSheetState.COLLAPSED -> collapsedHeight
        BottomSheetState.HALF_EXPANDED -> halfExpandedHeight
        BottomSheetState.EXPANDED -> expandedHeight
    }

    val animatedOffset by animateFloatAsState(
        targetValue = currentHeight + dragOffset,
        animationSpec = tween(durationMillis = 300),
        label = "bottomSheetOffset"
    )

    Column(
        modifier = modifier
            .offset(y = with(density) { animatedOffset.toDp() })
            .pointerInput(Unit) {
                detectDragGestures(
                    onDragEnd = {
                        val totalOffset = currentHeight + dragOffset
                        val collapsedDistance = abs(totalOffset - collapsedHeight)
                        val halfExpandedDistance = abs(totalOffset - halfExpandedHeight)
                        val expandedDistance = abs(totalOffset - expandedHeight)

                        currentState = when {
                            collapsedDistance <= halfExpandedDistance && collapsedDistance <= expandedDistance -> {
                                BottomSheetState.COLLAPSED
                            }

                            halfExpandedDistance <= expandedDistance -> {
                                BottomSheetState.HALF_EXPANDED
                            }

                            else -> {
                                BottomSheetState.EXPANDED
                            }
                        }
                        dragOffset = 0f
                    }
                ) { change, dragAmount ->
                    change.consume()
                    dragOffset += dragAmount.y
                }
            }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 10.dp, end = 18.dp),
            horizontalArrangement = Arrangement.End
        ) {
            menuContent()
        }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .height(with(density) { expandedHeight.toDp() })
                .clip(RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp))
                .background(Color.White)
                .padding(horizontal = 16.dp, vertical = 12.dp),
            verticalArrangement = Arrangement.Top
        ) {
            Box(
                modifier = Modifier
                    .size(width = 40.dp, height = 4.dp)
                    .align(Alignment.CenterHorizontally)
                    .clip(RoundedCornerShape(2.dp))
                    .background(SolplyTheme.colors.gray300)
            )
            Spacer(modifier = Modifier.height(16.dp))
            content()
        }
    }
}
