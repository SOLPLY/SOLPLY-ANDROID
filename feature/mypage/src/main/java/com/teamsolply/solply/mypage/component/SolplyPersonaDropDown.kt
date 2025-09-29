package com.teamsolply.solply.mypage.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.teamsolply.solply.designsystem.component.dropdown.SolplyBasicDropDown
import com.teamsolply.solply.designsystem.theme.SolplyTheme
import com.teamsolply.solply.mypage.model.DropDownPersonaItem
import com.teamsolply.solply.ui.extension.customClickable
import kotlinx.collections.immutable.persistentListOf

@Composable
fun SolplyPersonaDropDown(
    placeholder: String,
    onClickItem: (Int) -> Unit,
    onClickDropIcon: () -> Unit,
    dropDownContents: List<DropDownPersonaItem>,
    selectedIndex: Int,
    modifier: Modifier = Modifier,
    isDropped: Boolean = false,
    isSelected: Boolean = false
) {
    SolplyBasicDropDown(
        defaultLabel = if (isSelected) dropDownContents.get(selectedIndex).label else placeholder,
        onClickDropIcon = onClickDropIcon,
        isDropped = isDropped,
        modifier = modifier
    ) {
        dropDownContents.forEachIndexed { index, item ->
            if (index != selectedIndex) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            color = SolplyTheme.colors.white
                        )
                        .customClickable(
                            rippleEnabled = false,
                            onClick = { onClickItem(index) }
                        ),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start
                ) {
                    Text(
                        text = item.label,
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

@Preview
@Composable
private fun SolplyPersonaDropDownPreview() {
    var isDropped by remember { mutableStateOf(false) }
    var isSelected by remember { mutableStateOf(false) }
    var selectedIndex by remember { mutableIntStateOf(-1) }
    SolplyTheme {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(SolplyTheme.colors.white)
        ) {
            SolplyPersonaDropDown(
                placeholder = "조용한 공간에 오래 머물고 싶어요",
                onClickItem = {},
                onClickDropIcon = {},
                dropDownContents = persistentListOf(
                    DropDownPersonaItem(
                        "이곳저곳 둘러보고 싶어요"
                    ),
                    DropDownPersonaItem(
                        "취향이 담긴 곳을 찾고 싶어요"
                    ),
                    DropDownPersonaItem(
                        "자연을 감상하며 쉬고 싶어요"
                    ),
                    DropDownPersonaItem(
                        "조용한 공간에 오래 머물고 싶어요"
                    )
                ),
                isDropped = false,
                selectedIndex = 0,
                isSelected = false
            )
            SolplyPersonaDropDown(
                isDropped = isDropped,
                placeholder = "선택해주세요",
                onClickItem = {
                    selectedIndex = it
                    isSelected = true
                },
                onClickDropIcon = { isDropped = !isDropped },
                dropDownContents = persistentListOf(
                    DropDownPersonaItem(
                        "이곳저곳 둘러보고 싶어요"
                    ),
                    DropDownPersonaItem(
                        "취향이 담긴 곳을 찾고 싶어요"
                    ),
                    DropDownPersonaItem(
                        "자연을 감상하며 쉬고 싶어요"
                    ),
                    DropDownPersonaItem(
                        "조용한 공간에 오래 머물고 싶어요"
                    )
                ),
                selectedIndex = selectedIndex,
                isSelected = isSelected
            )
        }
    }
}
