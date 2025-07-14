package com.teamsolply.solply.mypage.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.teamsolply.solply.designsystem.component.card.SolplyCourseCard
import com.teamsolply.solply.designsystem.theme.SolplyTheme
import com.teamsolply.solply.model.PlaceType
import com.teamsolply.solply.ui.preview.DefaultPreview

@Composable
fun SolplyTownCard(
    town: String,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    Column {
        Box(
            modifier = modifier
                .clip(
                    shape = RoundedCornerShape(20.dp)
                ),
            contentAlignment = Alignment.Center
        ) {
            content.invoke()
            Image(
                painter = painterResource(com.teamsolply.solply.designsystem.R.drawable.img_solply_cover),
                contentDescription = "솔플리 커버",
                modifier = Modifier.matchParentSize()
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = town,
            style = SolplyTheme.typography.title15M,
            color = SolplyTheme.colors.black,
            modifier = Modifier.padding(start = 8.dp)
        )
    }
}

@DefaultPreview
@Composable
private fun TownCardPreview() {
    SolplyTheme {
        Column {
            SolplyTownCard(
                town = "연희동",
                content = {
                    Image(
                        painter = painterResource(com.teamsolply.solply.designsystem.R.drawable.img_course_dummy),
                        contentDescription = "동네 수집함"
                    )
                }
            )
            SolplyTownCard(
                town = "연희동",
                content = {
                    SolplyCourseCard(
                        title = "오감으로 수집하는 하루",
                        imgRes = com.teamsolply.solply.designsystem.R.drawable.img_course_dummy,
                        placeType = listOf(PlaceType.BOOK, PlaceType.SHOPPING),
                        backgroundColor = SolplyTheme.colors.purple200,
                        iconColor = SolplyTheme.colors.purple400,
                        iconBackGroundColor = SolplyTheme.colors.purple100,
                        savedPlace = true
                    )
                }
            )
        }
    }
}
