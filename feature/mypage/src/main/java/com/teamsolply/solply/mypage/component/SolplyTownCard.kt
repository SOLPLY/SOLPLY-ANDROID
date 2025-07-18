package com.teamsolply.solply.mypage.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
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
import com.teamsolply.solply.designsystem.theme.SolplyTheme

@Composable
fun SolplyTownCard(
    town: String,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    Column {
        Box(
            modifier = modifier
                .fillMaxWidth()
                .aspectRatio(1f)
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
