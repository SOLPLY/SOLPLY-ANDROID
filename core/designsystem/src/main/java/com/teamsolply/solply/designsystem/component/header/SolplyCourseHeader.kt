package com.teamsolply.solply.designsystem.component.header

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.teamsolply.solply.designsystem.theme.SolplyTheme

@Composable
fun CourseHeader(
    townName: String,
    persona: String,
    nickname: String,
    modifier: Modifier = Modifier
) {
    val recommendText = when (persona){
        "HEALING" -> "차분함을 좋아하는\n${nickname}님을 위한 오늘의 코스"
        "EXPLORER" -> "골목 곳곳을 탐색하는\n${nickname}님을 위한 오늘의 코스"
        "MOODING" -> "취향을 모으는\n${nickname}님을 위한 오늘의 코스"
        "NATURAL" -> "힐링이 필요한\n${nickname}님을 위한 오늘의 코스"
        else ->"솔플리가 추천하는\n${nickname}님을 위한 오늘의 코스"
    }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(top = 16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    painter = painterResource(id = com.teamsolply.solply.designsystem.R.drawable.ic_home_location),
                    contentDescription = "town-icon",
                    modifier = Modifier
                        .padding(1.dp)
                        .size(20.dp)
                )
                Text(
                    text = townName,
                    style = SolplyTheme.typography.body16M,
                    modifier = Modifier
                        .padding(horizontal = 4.dp)
                        .height(21.dp)
                )
                Icon(
                    painter = painterResource(id = com.teamsolply.solply.designsystem.R.drawable.ic_arrow_right_icon),
                    contentDescription = "arrow-right-icon",
                    modifier = Modifier
                        .padding(vertical = 7.dp)
                        .size(24.dp)
                )
            }
            Icon(
                painter = painterResource(id = com.teamsolply.solply.designsystem.R.drawable.ic_setting_icon),
                contentDescription = "setting-icon",
                modifier = Modifier
                    .padding(1.dp)
                    .size(24.dp)
            )
        }
        Text(
            text = recommendText,
            style = SolplyTheme.typography.display20Sb,
            modifier = Modifier
                .padding(start = 4.dp, top = 16.dp, bottom = 9.dp)
        )
    }
}
