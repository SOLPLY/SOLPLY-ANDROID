package com.teamsolply.solply.registerplace.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.teamsolply.solply.designsystem.theme.SolplyTheme
import com.teamsolply.solply.ui.extension.customClickable

@Composable
internal fun RegisterPlaceItem(
    placeName: String,
    placeAddress: String,
    modifier: Modifier = Modifier,
    onClick: (String, String) -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 16.dp)
            .customClickable(rippleEnabled = false) {
                onClick(placeName, placeAddress)
            }
    ) {
        Text(
            text = placeName,
            modifier = Modifier.padding(bottom = 7.dp),
            style = SolplyTheme.typography.title15M,
            color = SolplyTheme.colors.black,
        )
        Text(
            text = placeAddress,
            style = SolplyTheme.typography.caption12R,
            color = SolplyTheme.colors.gray700,
        )
    }
}