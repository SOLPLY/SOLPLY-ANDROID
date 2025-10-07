package com.teamsolply.solply.registerplace.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.teamsolply.solply.designsystem.component.dropdown.SolplyBasicDropDown
import com.teamsolply.solply.designsystem.theme.SolplyTheme
import com.teamsolply.solply.model.PlaceType
import com.teamsolply.solply.ui.extension.customClickable

@Composable
internal fun PlaceTypeDropDown(
    placeholder: String,
    onClickItem: (PlaceType) -> Unit,
    onClickDropIcon: () -> Unit,
    dropDownContents: List<PlaceType>,
    selectedPlaceType: PlaceType?,
    modifier: Modifier = Modifier,
    isSelected: Boolean = false,
    isDropped: Boolean = false,
) {
    SolplyBasicDropDown(
        defaultLabel = if (isSelected) selectedPlaceType!!.displayName else placeholder,
        onClickDropIcon = onClickDropIcon,
        isDropped = isDropped,
        modifier = modifier
    ) {
        dropDownContents.forEachIndexed { index, item ->
            if (item != selectedPlaceType) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            color = SolplyTheme.colors.white
                        )
                        .customClickable(
                            rippleEnabled = false,
                            onClick = { onClickItem(item) }
                        ),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start
                ) {
                    Text(
                        text = item.displayName,
                        color = SolplyTheme.colors.gray900,
                        style = SolplyTheme.typography.body16M,
                        modifier = Modifier.padding(
                            start = 20.dp,
                            top = 14.dp,
                            bottom = 14.dp
                        )
                    )
                }
                HorizontalDivider(color = SolplyTheme.colors.gray300)
            }
        }
    }
}