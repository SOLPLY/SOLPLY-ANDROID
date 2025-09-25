package com.teamsolply.solply.mypage.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.teamsolply.solply.designsystem.theme.SolplyTheme
import com.teamsolply.solply.mypage.R
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
    isDropped: Boolean = false,
    isSelected: Boolean = false
) {
    val borderColor = SolplyTheme.colors.gray100
    Column(
        modifier = Modifier
            .clip(
                RoundedCornerShape(20.dp)
            )
            .border(
                width = 1.dp,
                color = SolplyTheme.colors.gray300,
                shape = RoundedCornerShape(20.dp)
            )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .drawBehind {
                    val strokeWidth = 1.dp.toPx()
                    val y = size.height - strokeWidth / 2
                    drawLine(
                        color = borderColor,              // 선 색상
                        start = Offset(0f, y),           // 왼쪽 시작점
                        end = Offset(size.width, y),     // 오른쪽 끝점
                        strokeWidth = strokeWidth
                    )
                }
                .then(
                    if (isDropped) {
                        Modifier.background(
                            color = SolplyTheme.colors.gray100
                        )
                    } else {
                        Modifier.background(
                            color = SolplyTheme.colors.white
                        )
                    }
                ),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = if (isSelected) {
                    dropDownContents.get(selectedIndex).label
                } else {
                    placeholder
                },
                color = SolplyTheme.colors.gray900,
                style = SolplyTheme.typography.body16M,
                modifier = Modifier.padding(start = 20.dp)
            )
            Icon(
                painter = painterResource(R.drawable.ic_arrow_down),
                contentDescription = "",
                modifier = Modifier
                    .padding(end = 20.dp, top = 14.dp, bottom = 14.dp)
                    .height(24.dp)
                    .width(24.dp)
                    .scale(
                        scaleX = 1f,
                        scaleY = if (isDropped) -1f else 1f
                    )
                    .customClickable(
                        rippleEnabled = false,
                        onClick = onClickDropIcon
                    )
            )
        }
        HorizontalDivider(color = SolplyTheme.colors.gray300)
        AnimatedVisibility(
            visible = isDropped,
            enter = slideInVertically(),
            exit = slideOutVertically()
        ) {
            Column(
                modifier = Modifier.fillMaxWidth()
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
                dropDownContents = persistentListOf(),
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
                    ),
                ),
                selectedIndex = selectedIndex,
                isSelected = isSelected
            )
        }
    }
}