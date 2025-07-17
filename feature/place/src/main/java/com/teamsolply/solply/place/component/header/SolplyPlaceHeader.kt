package com.teamsolply.solply.designsystem.component.header

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.teamsolply.solply.designsystem.theme.SolplyTheme
import com.teamsolply.solply.ui.extension.customClickable

@Composable
fun PlaceHeader(
    townName: String,
    modifier: Modifier = Modifier,
    onClickTownName: () -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(top = 11.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, end = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .customClickable(
                        rippleEnabled = false
                    ) { onClickTownName() }
            ) {
                Icon(
                    painter = painterResource(id = com.teamsolply.solply.designsystem.R.drawable.ic_home_location),
                    contentDescription = "town-icon",
                    modifier = Modifier
                        .padding(1.dp)
                        .size(20.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = townName,
                    style = SolplyTheme.typography.body16M,
                    modifier = Modifier
                        .padding(start = 4.dp)
                        .height(21.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
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
    }
}
