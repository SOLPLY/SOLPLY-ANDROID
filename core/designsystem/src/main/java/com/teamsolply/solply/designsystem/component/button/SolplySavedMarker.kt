package com.teamsolply.solply.designsystem.component.button

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.teamsolply.solply.designsystem.R
import com.teamsolply.solply.ui.extension.customClickable

@Composable
fun SolplySavedMarker(
    iconColor: Color,
    iconBackGroundColor: Color,
    modifier: Modifier = Modifier,
    isButton: Boolean = false,
    onClick: () -> Unit = {}
) {
    Box(
        modifier = modifier
            .background(
                color = iconBackGroundColor,
                shape = CircleShape
            )
            .then(
                if (isButton) {
                    Modifier.customClickable(rippleEnabled = false) {
                        onClick()
                    }
                } else {
                    Modifier
                }
            ),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            painter = painterResource(R.drawable.ic_marker_default),
            contentDescription = "course",
            modifier = Modifier
                .padding(5.dp)
                .size(15.dp),
            tint = iconColor
        )
    }
}
