package com.teamsolply.solply.registerplace.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.teamsolply.solply.designsystem.R
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
    isDropped: Boolean = false
) {
    val defaultText = selectedPlaceType?.displayName ?: placeholder

    SolplyBasicDropDown(
        defaultLabel = {
            Row(
                modifier = Modifier.padding(start = 20.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start
            ) {
                if (selectedPlaceType != null) {
                    Icon(
                        painter = painterResource(id = getPlaceTypeIconResId(selectedPlaceType)),
                        contentDescription = "place_type",
                        modifier = Modifier.padding(end = 4.dp)
                    )
                }
                Text(
                    text = defaultText,
                    color = SolplyTheme.colors.gray900,
                    style = SolplyTheme.typography.body16M
                )
            }
        },
        onClickDropIcon = onClickDropIcon,
        isDropped = isDropped,
        modifier = modifier
    ) {
        dropDownContents.forEachIndexed { index, item ->
            if (item != selectedPlaceType) {
                val dropDownIcon = getPlaceTypeIconResId(item)
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
                    Icon(
                        painter = painterResource(id = dropDownIcon),
                        contentDescription = "place_type",
                        modifier = Modifier.padding(
                            start = 20.dp,
                            end = 4.dp,
                            top = 14.dp,
                            bottom = 14.dp
                        )
                    )
                    Text(
                        text = item.displayName,
                        color = SolplyTheme.colors.gray900,
                        style = SolplyTheme.typography.body16R
                    )
                }
                HorizontalDivider(color = SolplyTheme.colors.gray300)
            }
        }
    }
}

fun getPlaceTypeIconResId(item: PlaceType): Int {
    return when (item) {
        PlaceType.ALL -> R.drawable.ic_caffe
        PlaceType.CAFE -> R.drawable.ic_caffe
        PlaceType.FOOD -> R.drawable.ic_food
        PlaceType.BOOKSTORE -> R.drawable.ic_book
        PlaceType.WALKING -> R.drawable.ic_walk
        PlaceType.SHOPPING -> R.drawable.ic_shopping
        PlaceType.UNIQUE_SPACE -> R.drawable.ic_unique
    }
}
