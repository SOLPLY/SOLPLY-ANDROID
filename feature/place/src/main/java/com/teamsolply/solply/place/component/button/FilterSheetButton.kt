package com.teamsolply.solply.place.component.button

import androidx.compose.foundation.background
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
import com.teamsolply.solply.designsystem.theme.SolplyTheme
import com.teamsolply.solply.ui.extension.customClickable

@Composable
fun FilterSheetButton(
    iconRes: Int,
    label: String,
    selected: Boolean,
    showCheck: Boolean,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(46.dp)
            .customClickable(rippleEnabled = false) { onClick() }
            .background(SolplyTheme.colors.white),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(iconRes),
            contentDescription = label,
            modifier = Modifier
                .padding(horizontal = 16.dp),
            tint = SolplyTheme.colors.gray900
        )
        Text(
            text = label,
            style = SolplyTheme.typography.body16M,
            color = SolplyTheme.colors.gray900,
            modifier = Modifier.padding(start = 16.dp)
        )
        Spacer(modifier = Modifier.weight(1f))
        if (showCheck) {
            Icon(
                painter = painterResource(com.teamsolply.solply.designsystem.R.drawable.ic_filter_selected),
                contentDescription = "selected",
                modifier = Modifier,
                tint = Color.Unspecified
            )
        }
    }
}

