package com.teamsolply.solply.place.component.bottomsheet

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.teamsolply.solply.designsystem.theme.SolplyTheme
import com.teamsolply.solply.place.OptionTag
import com.teamsolply.solply.place.component.button.FilterChipButton
import com.teamsolply.solply.ui.extension.customClickable


@Composable
fun PlaceOptionFilterSheet(
    optionTags: List<OptionTag>,
    selectedOptionIds: List<Int>,
    onOptionSelected: (Int) -> Unit,
    onDismiss: () -> Unit,
    onReset: () -> Unit,
    onDone: () -> Unit
) {
    val option1Tags = optionTags.filter { it.tagType == "OPTION1" }
    val option2Tags = optionTags.filter { it.tagType == "OPTION2" }

    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        item {
            if (option1Tags.isNotEmpty()) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 24.dp, bottom = 20.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "방문 목적",
                        style = SolplyTheme.typography.title18Sb,
                        modifier = Modifier.weight(1f)
                    )

                    Icon(
                        painter = painterResource(com.teamsolply.solply.designsystem.R.drawable.ic_close),
                        contentDescription = "close",
                        modifier = Modifier
                            .size(24.dp)
                            .customClickable { onDismiss() }
                    )
                }


                ChipRow(
                    tags = option1Tags,
                    selectedOptionIds = selectedOptionIds,
                    onOptionSelected = onOptionSelected
                )

            }



//            if (option2Tags.isNotEmpty()) {
//                Text(
//                    text = "편의시설",
//                    style = SolplyTheme.typography.title18Sb,
//                    modifier = Modifier.padding(bottom = 20.dp)
//                )
//
//                ChipRow(
//                    tags = option2Tags,
//                    selectedOptionIds = selectedOptionIds,
//                    onOptionSelected = onOptionSelected
//                )
//
//                Spacer(modifier = Modifier.height(60.dp))
//
//                Row(
//                    Modifier.fillMaxWidth(),
//                    horizontalArrangement = Arrangement.SpaceBetween
//                ) {
//                    Text(
//                        "초기화",
//                        style = SolplyTheme.typography.body16M,
//                        modifier = Modifier
//                            .customClickable { onReset() }
//                            .padding(vertical = 18.dp)
//                    )
//                    Box(
//                        modifier = Modifier
//                            .background(
//                                SolplyTheme.colors.gray900,
//                                shape = RoundedCornerShape(24.dp)
//                            )
//                            .customClickable { onDone() }
//                            .padding(horizontal = 40.dp, vertical = 18.dp)
//                    ) {
//                        Text(
//                            "완료",
//                            style = SolplyTheme.typography.body16M,
//                            color = SolplyTheme.colors.white
//                        )
//                    }
//                }
//            }
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ChipRow(
    tags: List<OptionTag>,
    selectedOptionIds: List<Int>,
    onOptionSelected: (Int) -> Unit
) {
    FlowRow {
        tags.forEach { tag ->
            FilterChipButton(
                text = tag.name,
                isSelected = selectedOptionIds.contains(tag.tagId),
                onClick = { onOptionSelected(tag.tagId) }
            )
        }
    }
}