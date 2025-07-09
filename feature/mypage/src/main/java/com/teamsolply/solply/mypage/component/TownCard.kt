package com.teamsolply.solply.mypage.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.teamsolply.solply.designsystem.theme.SolplyTheme
import com.teamsolply.solply.ui.preview.DefaultPreview

@Composable
fun TownCard(
    town: String
) {
    Column {
        Box(
            modifier = Modifier
                .size(165.dp)
                .clip(
                    shape = RoundedCornerShape(20.dp)
                ),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(com.teamsolply.solply.designsystem.R.drawable.img_course_dummy),
                contentDescription = "동네 수집함"
            )
            Icon(
                painter = painterResource(com.teamsolply.solply.designsystem.R.drawable.ic_group_cover),
                contentDescription = "솔플리 커버",
                modifier = Modifier.fillMaxSize()
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
        TownCard(
            town = "연희동"
        )
    }
}