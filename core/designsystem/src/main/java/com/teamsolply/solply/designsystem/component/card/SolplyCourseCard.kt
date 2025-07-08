package com.teamsolply.solply.designsystem.component.card

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.teamsolply.solply.designsystem.component.button.SolplySavedMarker
import com.teamsolply.solply.designsystem.component.chip.CheckedBigCircle
import com.teamsolply.solply.designsystem.component.chip.PlaceTag
import com.teamsolply.solply.designsystem.theme.SolplyTheme
import com.teamsolply.solply.model.PlaceType
import com.teamsolply.solply.ui.extension.customClickable

@Composable
fun SolplyCourseCard(
    title: String,
    imgRes: Int,
    placeType: List<PlaceType>,
    backgroundColor: Color,
    iconColor: Color,
    iconBackGroundColor: Color,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
    saved: Boolean = false,
    selected: Boolean = false,
    touchable: Boolean = true
) {
    Box(
        modifier = modifier
            .size(165.dp)
            .alpha(if (touchable) 1f else 0.3f)
            .then(
                if (touchable) {
                    Modifier.customClickable(rippleEnabled = false) { onClick() }
                } else {
                    Modifier
                }
            ),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = imgRes),
            contentDescription = "course_image"
        )
        Box(
            modifier = Modifier
                .padding(top = 73.dp)
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
                .padding(top = 77.dp)
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
                    .padding(horizontal = 8.dp, vertical = 8.dp)
            ) {
                Row {
                    Text(
                        text = title,
                        modifier = Modifier
                            .weight(1f)
                            .padding(end = 12.dp),
                        style = SolplyTheme.typography.body14M,
                        color = SolplyTheme.colors.black,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )
                    if (saved) {
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
                    .align(Alignment.TopStart)
                    .padding(start = 18.dp, top = 16.dp)
            )
        }
    }
}
