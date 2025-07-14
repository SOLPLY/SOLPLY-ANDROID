package com.teamsolply.solply.place.component.bottomsheet

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.teamsolply.solply.designsystem.theme.SolplyTheme
import com.teamsolply.solply.place.component.button.FilterSheetButton
import com.teamsolply.solply.place.model.PlaceTypeFilterItem
import com.teamsolply.solply.ui.extension.customClickable

@Composable
fun PlaceTypeFilterSheet(
    filterItems: List<PlaceTypeFilterItem>,
    selectedType: String,
    onSelectType: (String) -> Unit,
    onDismiss: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 24.dp, bottom = 20.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "장소 유형",
                style = SolplyTheme.typography.title18Sb,
                modifier = Modifier.weight(1f)
            )
            Icon(
                painter = painterResource(com.teamsolply.solply.designsystem.R.drawable.ic_close),
                contentDescription = "close",
                modifier = Modifier.size(24.dp).customClickable { onDismiss() }
            )
        }
        filterItems.forEachIndexed { idx, item ->
            FilterSheetButton(
                iconRes = item.iconRes,
                label = item.label,
                selected = selectedType == item.type,
                showCheck = selectedType == item.type,
                onClick = { onSelectType(item.type) }
            )
            if (idx < filterItems.size - 1) {
                HorizontalDivider(thickness = 1.dp, color = SolplyTheme.colors.gray100)
            }
        }
        Spacer(modifier = Modifier.height(48.dp))
    }
}
