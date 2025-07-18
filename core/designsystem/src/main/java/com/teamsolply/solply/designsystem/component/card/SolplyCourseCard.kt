package com.teamsolply.solply.designsystem.component.card

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.teamsolply.solply.designsystem.component.button.SolplySavedMarker
import com.teamsolply.solply.designsystem.component.chip.CheckedBigCircle
import com.teamsolply.solply.designsystem.component.chip.PlaceTag
import com.teamsolply.solply.designsystem.theme.SolplyTheme
import com.teamsolply.solply.model.PlaceType
import com.teamsolply.solply.ui.extension.customClickable
import com.teamsolply.solply.ui.image.AdaptationImage

@Composable
fun SolplyCourseCard(
    title: String,
    imgRes: String,
    placeType: List<PlaceType>,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
    savedCourse: Boolean = false,
    selected: Boolean = false,
    savedPlace: Boolean = false
) {
    val backgroundColor = when (placeType.first()) {
        PlaceType.CAFE -> SolplyTheme.colors.red300
        PlaceType.FOOD -> SolplyTheme.colors.yellow200
        PlaceType.WALKING, PlaceType.UNIQUE_SPACE -> SolplyTheme.colors.green300
        PlaceType.SHOPPING, PlaceType.BOOKSTORE -> SolplyTheme.colors.purple300
        else -> SolplyTheme.colors.gray300
    }
    val iconColor = when (placeType.first()) {
        PlaceType.CAFE -> SolplyTheme.colors.red500
        PlaceType.FOOD -> SolplyTheme.colors.yellow300
        PlaceType.WALKING, PlaceType.UNIQUE_SPACE -> SolplyTheme.colors.green400
        PlaceType.SHOPPING, PlaceType.BOOKSTORE -> SolplyTheme.colors.purple400
        else -> SolplyTheme.colors.gray400
    }
    val iconBackGroundColor = when (placeType.first()) {
        PlaceType.CAFE -> SolplyTheme.colors.red200
        PlaceType.FOOD -> SolplyTheme.colors.yellow100
        PlaceType.WALKING, PlaceType.UNIQUE_SPACE -> SolplyTheme.colors.green200
        PlaceType.SHOPPING, PlaceType.BOOKSTORE -> SolplyTheme.colors.purple100
        else -> SolplyTheme.colors.gray200
    }

    Box(
        modifier = modifier
            .fillMaxWidth()
            .aspectRatio(1f)
            .alpha(if (savedPlace) 0.3f else 1f)
            .customClickable(rippleEnabled = false) { onClick() },
        contentAlignment = Alignment.Center
    ) {
        AdaptationImage(
            imageUrl = imgRes,
            modifier = Modifier
                .matchParentSize()
                .clip(
                    RoundedCornerShape(20.dp)
                ),
            contentScale = ContentScale.Crop
        )
        Box(
            modifier = Modifier
                .padding(top = 70.dp)
                .matchParentSize()
                .background(
                    color = SolplyTheme.colors.black.copy(alpha = 0.3f),
                    shape = RoundedCornerShape(
                        bottomStart = 20.dp,
                        bottomEnd = 20.dp
                    )
                )

        )
        Box(
            modifier = Modifier
                .padding(top = 74.dp)
                .background(
                    color = backgroundColor,
                    shape = RoundedCornerShape(
                        topStart = 4.dp,
                        topEnd = 4.dp,
                        bottomStart = 20.dp,
                        bottomEnd = 20.dp
                    )
                )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 12.dp, end = 12.dp, top = 8.dp, bottom = 12.dp)
            ) {
                Row {
                    Text(
                        text = title,
                        modifier = Modifier
                            .weight(1f)
                            .padding(end = 12.dp),
                        style = SolplyTheme.typography.body14M.copy(
                            lineHeight = 18.sp
                        ),
                        color = SolplyTheme.colors.black,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )
                    if (savedCourse) {
                        SolplySavedMarker(
                            iconColor = iconColor,
                            iconBackGroundColor = iconBackGroundColor,
                            isButton = false
                        )
                    }
                }
                Spacer(modifier = Modifier.weight(1f))
                Row(
                    modifier = Modifier
                        .padding(bottom = 4.dp)
                ) {
                    placeType.take(2).forEach { placeType ->
                        PlaceTag(
                            type = placeType,
                            modifier = Modifier.padding(end = 4.dp)
                        )
                    }
                }
            }
        }

        if (selected) {
            CheckedBigCircle(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(end = 12.dp, top = 16.dp)
            )
        }
    }
}
