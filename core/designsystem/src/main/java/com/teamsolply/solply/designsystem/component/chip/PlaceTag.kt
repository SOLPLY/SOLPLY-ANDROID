package com.teamsolply.solply.designsystem.component.chip

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.teamsolply.solply.designsystem.theme.SolplyTheme
import com.teamsolply.solply.model.PlaceType

@Composable
fun PlaceTag(
    type: PlaceType,
    modifier: Modifier = Modifier
) {
    val textColor = when (type) {
        PlaceType.CAFE -> SolplyTheme.colors.red500
        PlaceType.FOOD -> SolplyTheme.colors.yellow500
        PlaceType.BOOK -> SolplyTheme.colors.purple600
        PlaceType.WALK -> SolplyTheme.colors.gray500
        PlaceType.SHOPPING -> SolplyTheme.colors.purple600
        PlaceType.UNIQUE -> SolplyTheme.colors.green500
    }
    val backGroundColor = when (type) {
        PlaceType.CAFE -> SolplyTheme.colors.red100
        PlaceType.FOOD -> SolplyTheme.colors.yellow100
        PlaceType.BOOK -> SolplyTheme.colors.purple100
        PlaceType.WALK -> SolplyTheme.colors.green100
        PlaceType.SHOPPING -> SolplyTheme.colors.purple100
        PlaceType.UNIQUE -> SolplyTheme.colors.green100
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
            style = SolplyTheme.typography.caption12M,
            color = textColor
        )
    }
}
