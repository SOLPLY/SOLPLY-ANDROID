package com.teamsolply.solply.onboarding.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.teamsolply.solply.ui.extension.customClickable

@Composable
fun BackHeader(
    barText: String,
    onBackButtonClick: () -> Unit,
    isTownSelected: Boolean
) {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(com.teamsolply.solply.designsystem.R.drawable.ic_back),
            contentDescription = "back",
            tint = Color.Unspecified,
            modifier = Modifier
                .padding(start = 16.dp, bottom = 24.dp)
                .customClickable(rippleEnabled = false) { onBackButtonClick() }
        )
    }
}
