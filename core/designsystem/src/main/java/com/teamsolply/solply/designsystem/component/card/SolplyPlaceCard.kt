package com.teamsolply.solply.designsystem.component.card

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImagePainter
import com.teamsolply.solply.designsystem.component.button.SolplySavedMarker
import com.teamsolply.solply.designsystem.component.chip.CheckedBigCircle
import com.teamsolply.solply.designsystem.component.chip.PlaceTag
import com.teamsolply.solply.designsystem.theme.SolplyTheme
import com.teamsolply.solply.model.PlaceType
import com.teamsolply.solply.ui.extension.customClickable
import com.teamsolply.solply.ui.image.AdaptationImage

@Composable
fun SolplyPlaceCard(
    name: String,
    imgRes: String,
    placeType: PlaceType,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
    saved: Boolean = false,
    selected: Boolean = false,
    touchable: Boolean = true
) {
    var isImageReady by remember { mutableStateOf(false) }

    val iconColor = when (placeType) {
        PlaceType.CAFE -> SolplyTheme.colors.red500
        PlaceType.FOOD -> SolplyTheme.colors.yellow300
        PlaceType.WALKING, PlaceType.UNIQUE_SPACE -> SolplyTheme.colors.green400
        PlaceType.SHOPPING, PlaceType.BOOKSTORE -> SolplyTheme.colors.purple400
        else -> SolplyTheme.colors.gray400
    }
    val iconBackGroundColor = when (placeType) {
        PlaceType.CAFE -> SolplyTheme.colors.red500
        PlaceType.FOOD -> SolplyTheme.colors.yellow400
        PlaceType.WALKING, PlaceType.UNIQUE_SPACE -> SolplyTheme.colors.green500
        PlaceType.SHOPPING, PlaceType.BOOKSTORE -> SolplyTheme.colors.purple200
        else -> SolplyTheme.colors.purple500
    }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .then(
                if (touchable && isImageReady) {
                    Modifier.customClickable(rippleEnabled = false) { onClick() }
                } else {
                    Modifier
                }
            ),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1f)
                .clip(RoundedCornerShape(20.dp))
        ) {
            if (!isImageReady) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(SolplyTheme.colors.gray400.copy(alpha = 0.4f))
                )
            }

            AdaptationImage(
                imageUrl = imgRes,
                modifier = Modifier
                    .fillMaxSize()
                    .clip(RoundedCornerShape(20.dp)),
                contentScale = ContentScale.Crop,
                onLoadingStateChange = { state ->
                    if (state is AsyncImagePainter.State.Success) {
                        isImageReady = true
                    }
                }
            )

            if (saved && isImageReady) {
                SolplySavedMarker(
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .padding(12.dp),
                    iconColor = iconColor,
                    iconBackGroundColor = iconBackGroundColor,
                    isButton = false
                )
            }
            if (selected && isImageReady) {
                CheckedBigCircle(
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(12.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            PlaceTag(type = placeType)
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = name,
                style = SolplyTheme.typography.body14M,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.weight(1f)
            )
        }
    }
}
