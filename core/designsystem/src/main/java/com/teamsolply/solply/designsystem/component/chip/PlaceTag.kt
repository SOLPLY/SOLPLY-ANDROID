package com.teamsolply.solply.designsystem.component.chip

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.teamsolply.solply.designsystem.theme.SolplyTheme
import com.teamsolply.solply.model.PlaceType

@Composable
fun PlaceTag(
    type: PlaceType,
    modifier: Modifier = Modifier
) {
    val textColor = when(type) {
        PlaceType.CAFE -> SolplyTheme.colors.yellow400
        PlaceType.FOOD -> SolplyTheme.colors.red600
        PlaceType.BOOK -> SolplyTheme.colors.purple700
        PlaceType.WALK -> TODO()
        PlaceType.SHOPPING -> SolplyTheme.colors.purple700
        PlaceType.UNIQUE -> SolplyTheme.colors.purple500
    }
    val backGroundColor = when(type) {
        PlaceType.CAFE -> SolplyTheme.colors.yellow100
        PlaceType.FOOD -> SolplyTheme.colors.red200
        PlaceType.BOOK -> SolplyTheme.colors.purple200
        PlaceType.WALK -> TODO()
        PlaceType.SHOPPING -> SolplyTheme.colors.purple200
        PlaceType.UNIQUE -> SolplyTheme.colors.green200
    }

    Box(
        modifier = modifier
            .background(
                color = backGroundColor,
                shape = CircleShape
            ),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = type.displayName,
            modifier = Modifier.padding(vertical = 2.dp, horizontal = 6.dp),
            style = SolplyTheme.typography.button12M,
            color = textColor
        )
    }
}
