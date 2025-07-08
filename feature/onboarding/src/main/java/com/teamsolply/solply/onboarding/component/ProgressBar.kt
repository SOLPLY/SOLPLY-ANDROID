package com.teamsolply.solply.onboarding.component

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.teamsolply.solply.designsystem.theme.SolplyTheme

@Composable
fun ProgressBar(
    completionRatio: Int
) {
    var progress by remember { mutableFloatStateOf(0f) }

    val size by animateFloatAsState(
        targetValue = progress,
        animationSpec = tween(
            durationMillis = 1000,
            delayMillis = 200,
            easing = LinearOutSlowInEasing,
        ),
        label = "animation",
    )

    LaunchedEffect(key1 = completionRatio) {
        progress = completionRatio / 100f
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(8.dp)
            .padding(horizontal = 16.dp)
            .background(SolplyTheme.colors.gray300)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth(size)
                .height(8.dp)
                .background(SolplyTheme.colors.gray900)
                .animateContentSize()
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ProgressBarPreview() {
    ProgressBar (
        completionRatio=50
    )
}
