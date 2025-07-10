package com.teamsolply.solply.designsystem.component.card

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.teamsolply.solply.designsystem.R
import com.teamsolply.solply.designsystem.component.button.SolplySavedMarker
import com.teamsolply.solply.designsystem.component.chip.CheckedBigCircle
import com.teamsolply.solply.designsystem.component.chip.PlaceTag
import com.teamsolply.solply.designsystem.theme.SolplyTheme
import com.teamsolply.solply.model.PlaceType
import com.teamsolply.solply.ui.extension.customClickable
import com.teamsolply.solply.ui.preview.DefaultPreview

@Composable
fun SolplyPlaceCard(
    name: String,
    imgRes: Int,
    placeType: PlaceType,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
    saved: Boolean = false,
    selected: Boolean = false,
    touchable: Boolean = true
) {
    val iconColor = when (placeType) {
        PlaceType.CAFE -> SolplyTheme.colors.red500
        PlaceType.FOOD -> SolplyTheme.colors.yellow300
        PlaceType.WALK, PlaceType.UNIQUE -> SolplyTheme.colors.green400
        PlaceType.SHOPPING, PlaceType.BOOK -> SolplyTheme.colors.purple400
        else -> SolplyTheme.colors.gray400
    }
    val iconBackGroundColor = when (placeType) {
        PlaceType.CAFE -> SolplyTheme.colors.red200
        PlaceType.FOOD -> SolplyTheme.colors.yellow100
        PlaceType.WALK, PlaceType.UNIQUE -> SolplyTheme.colors.green200
        PlaceType.SHOPPING, PlaceType.BOOK -> SolplyTheme.colors.purple100
        else -> SolplyTheme.colors.gray200
    }
    Column(
        modifier = modifier
            .wrapContentSize()
            .then(
                if (touchable) {
                    Modifier.customClickable(rippleEnabled = false) { onClick() }
                } else {
                    Modifier
                }
            ),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start
    ) {
        Box(
            modifier = Modifier.clip(
                shape = RoundedCornerShape(20.dp)
            )
        ) {
            Image(
                painter = painterResource(id = imgRes),
                contentDescription = "place_image",
                modifier = Modifier
            )
            if (saved) {
                SolplySavedMarker(
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .padding(12.dp),
                    iconColor = iconColor,
                    iconBackGroundColor = iconBackGroundColor,
                    isButton = false
                )
            }
            if (selected) {
                CheckedBigCircle(
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(12.dp)
                )
            }
        }
        Spacer(modifier = Modifier.height(Dp(8f)))
        Row(
            modifier = Modifier,
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            PlaceTag(
                type = placeType
            )
            Spacer(modifier = Modifier.width(Dp(4f)))
            Text(
                text = name,
                style = SolplyTheme.typography.body14M
            )
        }
    }
}

@DefaultPreview
@Composable
private fun SolplyPlaceCardPreview() {
    SolplyTheme {
        Column(
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            SolplyPlaceCard(
                name = "을지면옥",
                imgRes = R.drawable.img_course_dummy,
                placeType = PlaceType.SHOPPING,
                saved = true,
                selected = true
            )
            SolplyPlaceCard(
                name = "을지면옥",
                imgRes = R.drawable.img_course_dummy,
                placeType = PlaceType.CAFE,
                saved = true,
                selected = false
            )
            SolplyPlaceCard(
                name = "을지면옥",
                imgRes = R.drawable.img_course_dummy,
                placeType = PlaceType.WALK,
                saved = false,
                selected = false
            )
        }
    }
}
