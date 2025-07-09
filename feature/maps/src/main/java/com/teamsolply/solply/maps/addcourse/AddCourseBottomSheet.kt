package com.teamsolply.solply.maps.addcourse

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.teamsolply.solply.designsystem.theme.SolplyTheme
import com.teamsolply.solply.maps.component.CourseItem
import com.teamsolply.solply.maps.model.Place
import com.teamsolply.solply.model.PlaceType

@Composable
fun AddCourseBottomSheet(
    places: List<Place>,
    courseName: String,
    introduction: String,
) {
    Column(
        modifier = Modifier.padding(horizontal = 20.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            text = courseName,
            modifier = Modifier.padding(bottom = 4.dp),
            color = SolplyTheme.colors.black,
            style = SolplyTheme.typography.title18Sb
        )
        Text(
            text = introduction,
            modifier = Modifier.padding(bottom = 20.dp),
            color = SolplyTheme.colors.gray900,
            style = SolplyTheme.typography.caption14R
        )
        Column(
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            places.forEachIndexed { index, place ->
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(20.dp)
                            .background(
                                color = SolplyTheme.colors.gray200,
                                shape = RoundedCornerShape(8.dp)
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = (index + 1).toString(),
                            color = SolplyTheme.colors.gray800,
                            style = SolplyTheme.typography.caption12M
                        )
                    }
                    Spacer(modifier = Modifier.width(14.dp))
                    CourseItem(
                        placeName = place.placeName,
                        placeTag = PlaceType.entries.firstOrNull {
                            it.displayName == place.primaryTag
                        } ?: PlaceType.EMPTY,
                        placeAddress = place.address,
                        //TODO. 코스의 장소 이미지로
                        //placeImageRes = item.thumbnailUrl
                        placeImageRes = com.teamsolply.solply.designsystem.R.drawable.img_course_dummy
                    )
                }
            }
        }
    }
}
