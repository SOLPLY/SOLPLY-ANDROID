package com.teamsolply.solply.place.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.teamsolply.solply.designsystem.component.chip.PlaceTag
import com.teamsolply.solply.designsystem.theme.SolplyTheme
import com.teamsolply.solply.model.PlaceType
import com.teamsolply.solply.ui.extension.customClickable

@Composable
fun PlaceRecommendCard(
    title: String,
    subtitle: String,
    type: PlaceType,
    imgRes: Int,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {}
) {
    Box(
        modifier
            .graphicsLayer { clip = true; shape = RoundedCornerShape(20.dp) }
            .customClickable(rippleEnabled = false) { onClick() }
    ) {
        Image(
            painter = painterResource(imgRes),
            contentDescription = title,
            modifier = Modifier.fillMaxSize(),
        )
        Box(
            Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        0f to Color.Transparent,
                        1f to SolplyTheme.colors.black
                    )
                )
        )
        Column(
            Modifier
                .align(Alignment.BottomStart)
                .padding(16.dp)
        ) {
            PlaceTag(type = type)
            Spacer(Modifier.height(4.dp))
            Text(
                text = title,
                style = SolplyTheme.typography.display16Sb,
                color = SolplyTheme.colors.white
            )
            Spacer(Modifier.height(4.dp))
            Text(subtitle,
                style = SolplyTheme.typography.display12R,
                color = SolplyTheme.colors.white,
                maxLines = 2
            )
        }
    }
}