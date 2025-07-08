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
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.teamsolply.solply.designsystem.theme.SolplyTheme

@Composable
fun ProgressBar(
    pageState: PagerState, totalpageCount : Int
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

    LaunchedEffect(key1 = pageState.currentPage) {
        progress = ((pageState.currentPage + 1).toFloat() / totalpageCount) * 0.95f
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(8.dp)
            .padding(horizontal = 16.dp)
            .clip(RoundedCornerShape(20.dp))
            .background(SolplyTheme.colors.gray300)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth(size)
                .height(8.dp)
                .background(SolplyTheme.colors.gray900, shape = RoundedCornerShape(20.dp))
                .clip(RoundedCornerShape(20.dp))
                .animateContentSize()
        )
    }
}

@Composable
fun PreviewableProgressBar(progress: Float) {
    val size by animateFloatAsState(
        targetValue = progress,
        animationSpec = tween(
            durationMillis = 1000,
            delayMillis = 200,
            easing = LinearOutSlowInEasing,
        ),
        label = "animation",
    )

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(8.dp)
            .padding(horizontal = 16.dp)
            .clip(RoundedCornerShape(20.dp))
            .background(SolplyTheme.colors.gray300)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth(size)
                .height(8.dp)
                .background(SolplyTheme.colors.gray900, shape = RoundedCornerShape(20.dp))
                .clip(RoundedCornerShape(20.dp))
                .animateContentSize()
        )
    }
}


@Preview(showBackground = true)
@Composable
fun ProgressBarPreview() {
    SolplyTheme {
        PreviewableProgressBar(progress = (2f / 3f) * 0.95f) // 약 63%
    }
}
