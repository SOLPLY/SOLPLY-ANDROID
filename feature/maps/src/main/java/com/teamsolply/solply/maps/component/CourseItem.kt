package com.teamsolply.solply.maps.component

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.material3.minimumInteractiveComponentSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.teamsolply.solply.designsystem.R
import com.teamsolply.solply.designsystem.component.chip.PlaceTag
import com.teamsolply.solply.designsystem.theme.SolplyTheme
import com.teamsolply.solply.model.PlaceType
import com.teamsolply.solply.ui.extension.customClickable
import com.teamsolply.solply.ui.image.AdaptationImage
import formatTextToPlaceItem
import formatTextToPlaceItemTitle

@Composable
internal fun CourseItem(
    placeName: String,
    placeTag: PlaceType,
    placeAddress: String,
    placeImageRes: String,
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

    val backGroundColor =
        if (selectedPlaceItem) SolplyTheme.colors.gray100 else SolplyTheme.colors.white

    Column(
        modifier = modifier
            .animateContentSize(animationSpec = tween(durationMillis = 70))
            .fillMaxWidth()
            .border(
                width = 1.dp,
                color = SolplyTheme.colors.gray300,
                shape = RoundedCornerShape(20.dp)
            )
            .background(
                color = backGroundColor,
                shape = RoundedCornerShape(20.dp)
            )
    ) {
        Row {
            AdaptationImage(
                imageUrl = placeImageRes,
                modifier = Modifier
                    .padding(8.dp)
                    .size(imageSize)
                    .clip(RoundedCornerShape(12.dp)),
                contentScale = ContentScale.Crop
            )
            Column {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Row(modifier = Modifier.padding(top = 12.dp, bottom = 8.dp)) {
                            PlaceTag(
                                type = placeTag,
                                modifier = Modifier.padding(end = 4.dp)
                            )
                            Text(
                                text = placeName.formatTextToPlaceItemTitle(),
                                modifier = Modifier,
                                color = SolplyTheme.colors.black,
                                style = SolplyTheme.typography.title15M
                            )
                        }
                        Text(
                            text = placeAddress.formatTextToPlaceItem(),
                            modifier = Modifier,
                            color = SolplyTheme.colors.gray700,
                            style = SolplyTheme.typography.caption12R
                        )
                    }
                    Spacer(modifier = Modifier.weight(1f))
                    Box(modifier = Modifier.padding(top = 10.dp)) {
                        if (isEditing) {
                            Icon(
                                painter = painterResource(R.drawable.ic_course_item_edit),
                                modifier = Modifier.size(28.dp),
                                contentDescription = "item_edting",
                                tint = SolplyTheme.colors.gray400
                            )
                        } else {
                            Icon(
                                painter = painterResource(if (iconSelected) R.drawable.ic_bookmark_fill else R.drawable.ic_bookmark_empty),
                                contentDescription = "saved marker",
                                modifier = Modifier.customClickable(rippleEnabled = false) {
                                    iconClick()
                                },
                                tint = if (iconSelected) Color.Unspecified else SolplyTheme.colors.gray400
                            )
                        }
                    }
                    Spacer(modifier = Modifier.padding(end = 22.dp))
                }
            }
        }
        if (selectedPlaceItem) {
            HorizontalDivider(
                thickness = 1.dp,
                color = SolplyTheme.colors.gray300
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "길찾기",
                    modifier = Modifier
                        .minimumInteractiveComponentSize()
                        .customClickable(rippleEnabled = false) { navigatePlaceClick() },
                    color = SolplyTheme.colors.gray800,
                    style = SolplyTheme.typography.button14M
                )
                VerticalDivider(
                    modifier = Modifier
                        .height(43.dp)
                        .padding(vertical = 4.dp),
                    thickness = 1.dp,
                    color = SolplyTheme.colors.gray300
                )
                Text(
                    text = "상세보기",
                    modifier = Modifier
                        .minimumInteractiveComponentSize()
                        .customClickable(rippleEnabled = false) { placeDetailClick() },
                    color = SolplyTheme.colors.gray800,
                    style = SolplyTheme.typography.button14M
                )
            }
        }
    }
}
