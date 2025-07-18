package com.teamsolply.solply.place.component.card

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.teamsolply.solply.designsystem.component.chip.PlaceTag
import com.teamsolply.solply.designsystem.theme.SolplyTheme
import com.teamsolply.solply.model.PlaceType
import com.teamsolply.solply.ui.extension.customClickable
import com.teamsolply.solply.ui.image.AdaptationImage

@Composable
fun PlaceRecommendCard(
    title: String,
    subtitle: String,
    type: PlaceType,
    imgRes: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
    scale: Float = 1f
) {
    Box(
        modifier
            .graphicsLayer { clip = true; shape = RoundedCornerShape(20.dp * scale) }
            .customClickable(rippleEnabled = false) { onClick() }
    ) {
        AdaptationImage(
            imageUrl = imgRes,
            contentDescription = title,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
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
                .padding(16.dp * scale)
        ) {
            if (scale < 1f) {
                Box(Modifier.scale(0.75f)) {
                    PlaceTag(type = type)
                }
            } else {
                PlaceTag(type = type)
            }
            Spacer(Modifier.height(4.dp * scale))
            Text(
                text = title,
                style = SolplyTheme.typography.display16Sb.copy(
                    fontSize = SolplyTheme.typography.display16Sb.fontSize * scale
                ),
                color = SolplyTheme.colors.white
            )
            Spacer(Modifier.height(4.dp * scale))
            Text(
                subtitle,
                style = SolplyTheme.typography.display12R.copy(
                    fontSize = SolplyTheme.typography.display12R.fontSize * scale
                ),
                color = SolplyTheme.colors.white,
                maxLines = 2
            )
        }
    }
}
