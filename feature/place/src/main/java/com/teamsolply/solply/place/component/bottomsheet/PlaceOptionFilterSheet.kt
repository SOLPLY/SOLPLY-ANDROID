package com.teamsolply.solply.place.component.bottomsheet

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.teamsolply.solply.designsystem.component.button.SolplyBasicButton
import com.teamsolply.solply.designsystem.theme.SolplyTheme
import com.teamsolply.solply.model.PlaceSubType
import com.teamsolply.solply.place.component.button.FilterChipButton
import com.teamsolply.solply.place.model.TagEntity
import com.teamsolply.solply.ui.extension.customClickable
import timber.log.Timber.Forest.tag

@Composable
fun PlaceOptionFilterSheet(
    optionTags: List<TagEntity>,
    selectedOptionIds: List<Int>,
    onOptionSelected: (Int, String) -> Unit,
    onDismiss: () -> Unit,
    onReset: () -> Unit,
    onDone: () -> Unit
) {
    val groupedTags = optionTags.groupBy { it.tagType }

    val bottomSheetHeight = 468.dp

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(bottomSheetHeight)
            .padding(horizontal = 16.dp)
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .padding(bottom = 96.dp)
        ) {
            groupedTags.entries.forEachIndexed { index, (tagType, tags) ->
                item {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 24.dp, bottom = 20.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "옵션 " + (index + 1),
                            style = SolplyTheme.typography.title18Sb,
                            modifier = Modifier.weight(1f)
                        )
                        if (index == 0) {
                            Icon(
                                painter = painterResource(com.teamsolply.solply.designsystem.R.drawable.ic_close),
                                contentDescription = "close",
                                modifier = Modifier
                                    .size(24.dp)
                                    .customClickable { onDismiss() }
                            )
                        }
                    }
                    ChipRow(
                        tags = tags,
                        selectedOptionIds = selectedOptionIds,
                        onOptionSelected = onOptionSelected
                    )
                }
            }
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .padding(start = 20.dp, end = 20.dp, bottom = 24.dp)
        ) {
            SolplyBasicButton(
                text = "초기화",
                onClick = onReset,
                modifier = Modifier.weight(1f),
                selected = false,
                textColor = SolplyTheme.colors.gray900,
                textStyle = SolplyTheme.typography.body16M,
                enabledBackgroundColor = SolplyTheme.colors.white,
                disabledBackgroundColor = SolplyTheme.colors.white
            )
            Spacer(modifier = Modifier.padding(8.dp))
            SolplyBasicButton(
                text = "완료",
                onClick = onDone,
                modifier = Modifier.weight(3f),
                selected = true,
                textColor = SolplyTheme.colors.white,
                textStyle = SolplyTheme.typography.body16M,
                enabledBackgroundColor = SolplyTheme.colors.gray900,
                disabledBackgroundColor = SolplyTheme.colors.gray900
            )
        }
    }
}

@Composable
fun ChipRow(
    tags: List<TagEntity>,
    selectedOptionIds: List<Int>,
    onOptionSelected: (Int, String) -> Unit
) {
    FlowRow(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        tags.forEach { tag ->
            FilterChipButton(
                text = PlaceSubType.valueOf(tag.name).displayName,
                isSelected = selectedOptionIds.contains(tag.tagId),
                onClick = { onOptionSelected(tag.tagId, tag.tagType) }
            )
        }
    }
}
