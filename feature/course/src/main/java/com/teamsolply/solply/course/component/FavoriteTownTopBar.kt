package com.teamsolply.solply.course.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.teamsolply.solply.designsystem.R
import com.teamsolply.solply.designsystem.theme.SolplyTheme
import com.teamsolply.solply.ui.extension.customClickable


@Composable
internal fun FavoriteTownTopBar(
    onBackButtonClick: () -> Unit,
    onReturnToHomeButtonClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 16.dp, top = 4.dp),
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(R.drawable.ic_back_long_arrow),
            contentDescription = "back",
            tint = Color.Unspecified,
            modifier = Modifier
                .padding(end = 12.dp)
                .customClickable(rippleEnabled = false) { onBackButtonClick() }
        )
        Text(
            text = "자주 가는 동네",
            style = SolplyTheme.typography.head16M,
            color = SolplyTheme.colors.black
        )
        Spacer(
            modifier = Modifier.weight(1f)
        )
        Icon(
            painter = painterResource(R.drawable.ic_home),
            contentDescription = "home",
            tint = Color.Unspecified,
            modifier = Modifier
                .size(24.dp)
                .customClickable(rippleEnabled = false) { onReturnToHomeButtonClick() }
        )
    }
}