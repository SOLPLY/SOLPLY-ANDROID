package com.teamsolply.solply.maps.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.teamsolply.solply.designsystem.component.chip.PlaceTag
import com.teamsolply.solply.designsystem.theme.SolplyTheme
import com.teamsolply.solply.model.PlaceType

@Composable
fun CourseItem(
    placeName: String,
    placeTag: PlaceType,
    placeAddress: String,
    placeImageRes: Int,
    modifier: Modifier = Modifier,
    selected: Boolean = false
) {
    Row(
        modifier = modifier
            .height(68.dp)
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
                .padding(start = 8.dp, end = 9.dp, top = 8.dp, bottom = 8.dp)
                .size(52.dp)
                .clip(RoundedCornerShape(12.dp))
        )
        Column {
            Row(modifier = Modifier.padding(bottom = 8.dp)) {
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
    }
}
