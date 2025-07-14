package com.teamsolply.solply.maps.component

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.teamsolply.solply.designsystem.R
import com.teamsolply.solply.designsystem.component.button.SolplySavedMarker
import com.teamsolply.solply.designsystem.component.chip.PlaceTag
import com.teamsolply.solply.designsystem.theme.SolplyTheme
import com.teamsolply.solply.model.PlaceType
import com.teamsolply.solply.ui.extension.customClickable

@Composable
internal fun CourseItem(
    placeName: String,
    placeTag: PlaceType,
    placeAddress: String,
    placeImageRes: Int,
    iconClick: () -> Unit,
    modifier: Modifier = Modifier,
    placeDetailClick: () -> Unit,
    navigatePlaceClick: () -> Unit,
    iconSelected: Boolean = false,
    selectedPlaceItem: Boolean,
    isEditing: Boolean = false
) {
    val imageSize by animateDpAsState(
        targetValue = if (selectedPlaceItem) 88.dp else 52.dp,
        animationSpec = tween(durationMillis = 50)
    )

    Row(
        modifier = modifier
            .fillMaxWidth()
            .border(
                width = 1.dp,
                color = SolplyTheme.colors.gray300,
                shape = RoundedCornerShape(20.dp)
            )
            .background(
                color = SolplyTheme.colors.white,
                shape = RoundedCornerShape(20.dp)
            ),
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(placeImageRes),
            contentDescription = "place_image",
            modifier = Modifier
                .padding(8.dp)
                .size(imageSize)
                .clip(RoundedCornerShape(12.dp))
        )
        Column {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Column {
                    Row(modifier = Modifier.padding(bottom = 6.dp)) {
                        PlaceTag(
                            type = placeTag,
                            modifier = Modifier.padding(end = 4.dp)
                        )
                        Text(
                            text = placeName,
                            modifier = Modifier,
                            color = SolplyTheme.colors.black,
                            style = SolplyTheme.typography.title15M
                        )
                    }
                    Text(
                        text = placeAddress,
                        modifier = Modifier,
                        color = SolplyTheme.colors.gray700,
                        style = SolplyTheme.typography.caption12R
                    )
                }
                Spacer(modifier = Modifier.weight(1f))
                if (isEditing) {
                    Icon(
                        painter = painterResource(R.drawable.ic_course_item_edit),
                        contentDescription = "item_edting"
                    )
                } else {
                    val iconColor =
                        if (iconSelected) SolplyTheme.colors.red500 else SolplyTheme.colors.gray400
                    val iconBackGroundColor =
                        if (iconSelected) SolplyTheme.colors.red300 else SolplyTheme.colors.gray200
                    SolplySavedMarker(
                        iconColor = iconColor,
                        iconBackGroundColor = iconBackGroundColor,
                        onClick = { iconClick() },
                        isButton = true
                    )
                }
                Spacer(modifier = Modifier.padding(end = 22.dp))
            }
            if (selectedPlaceItem) {
                Spacer(modifier = Modifier.height(8.dp))
                Row(
                    horizontalArrangement = Arrangement.Start
                ) {
                    Spacer(modifier = Modifier.padding(start = 8.dp))
                    Box(
                        modifier = Modifier
                            .border(
                                width = 1.dp,
                                color = SolplyTheme.colors.gray300,
                                shape = RoundedCornerShape(16.dp)
                            )
                            .background(
                                color = SolplyTheme.colors.gray200,
                                shape = RoundedCornerShape(16.dp)
                            )
                            .padding(horizontal = 15.dp, vertical = 8.dp)
                            .customClickable(rippleEnabled = false) { placeDetailClick() },
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "장소상세",
                            color = SolplyTheme.colors.black,
                            style = SolplyTheme.typography.button12M
                        )
                    }
                    Spacer(modifier = Modifier.padding(start = 8.dp))
                    Box(
                        modifier = Modifier
                            .border(
                                width = 1.dp,
                                color = SolplyTheme.colors.gray300,
                                shape = RoundedCornerShape(16.dp)
                            )
                            .background(
                                color = SolplyTheme.colors.gray200,
                                shape = RoundedCornerShape(16.dp)
                            )
                            .padding(horizontal = 15.dp, vertical = 8.dp)
                            .customClickable(rippleEnabled = false) { navigatePlaceClick() },
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "길찾기",
                            color = SolplyTheme.colors.black,
                            style = SolplyTheme.typography.button12M
                        )
                    }
                }
            }
        }
    }
}
