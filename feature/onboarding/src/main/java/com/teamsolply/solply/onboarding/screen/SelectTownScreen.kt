package com.teamsolply.solply.onboarding.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.teamsolply.solply.designsystem.component.button.AddLocalAreaButton
import com.teamsolply.solply.designsystem.component.button.SolplyBasicButton
import com.teamsolply.solply.designsystem.theme.SolplyTheme
import com.teamsolply.solply.onboarding.OnBoardingIntent
import com.teamsolply.solply.onboarding.OnBoardingState
import com.teamsolply.solply.onboarding.component.OnBoardingTownBottomSheet
import com.teamsolply.solply.onboarding.model.ParentTownEntity
import com.teamsolply.solply.onboarding.model.SubTownEntity
import com.teamsolply.solply.ui.extension.customClickable

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SelectTownScreen(
    state: OnBoardingState,
    onNextClick: () -> Unit,
    onBoardingIntent: (OnBoardingIntent) -> Unit
) {
    val townList = state.townList
    val isButtonEnabled = state.selectedTownId != null
    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true
    )
    val borderColor = SolplyTheme.colors.gray200

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 20.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column(
            modifier = Modifier
        ) {
            Text(
                text = "반가워요!\n가장 먼저 추천 받고 싶은 동네를\n선택해주세요.",
                style = SolplyTheme.typography.display20Sb,
                color = SolplyTheme.colors.black,
                modifier = Modifier
                    .padding(top = 24.dp, bottom = 28.dp)
            )

            LazyColumn {
                item {
                    if (state.selectedTownId == null) {
                        AddLocalAreaButton(
                            text = "",
                            onClick = { onBoardingIntent(OnBoardingIntent.ChangeTownBottomSheetShown) },
                            selected = false
                        )
                    } else {
                        AddLocalAreaButton(
                            text = townList.parentTowns
                                .flatMap { it.subTowns }
                                .find { it.townId == state.selectedTownId }
                                ?.townName ?: "",
                            onClick = {},
                            selected = true
                        )
                    }
                }
            }

            if (state.townBottomSheetShown) {
                OnBoardingTownBottomSheet(
                    sheetState = sheetState,
                    onDismissRequest = { onBoardingIntent(OnBoardingIntent.ChangeTownBottomSheetShown) }
                ) {
                    Box {
                        Column(modifier = Modifier.height(600.dp)) {
                            Text(
                                text = "동네 설정",
                                modifier = Modifier.padding(
                                    start = 16.dp,
                                    top = 24.dp,
                                    bottom = 20.dp
                                ),
                                style = SolplyTheme.typography.title18Sb,
                                color = SolplyTheme.colors.black
                            )

                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                            ) {
                                LeftRegionPane(
                                    borderColor = borderColor,
                                    regions = townList.parentTowns,
                                    selectedRegionId = state.selectedRegionId,
                                    onSelect = { id -> onBoardingIntent(OnBoardingIntent.ChangeRegion(id)) }
                                )
                                VerticalDivider(
                                    thickness = 1.dp,
                                    color = SolplyTheme.colors.gray100
                                )
                                RightTownPane(
                                    borderColor = borderColor,
                                    towns = townList.parentTowns
                                        .find { it.townId == state.selectedRegionId }
                                        ?.subTowns ?: emptyList(),
                                    selectedTownId = state.selectedTownId,
                                    onSelect = { id -> onBoardingIntent(OnBoardingIntent.OnTownSelected(id)) }
                                )
                            }
                        }
                        SolplyBasicButton(
                            text = "완료",
                            modifier = Modifier
                                .align(Alignment.BottomCenter)
                                .padding(bottom = 24.dp, start = 20.dp, end = 20.dp),
                            onClick = {
                                if (isButtonEnabled) {
                                    onBoardingIntent(OnBoardingIntent.ChangeTownBottomSheetShown)
                                }
                            },
                            selected = isButtonEnabled,
                            textStyle = SolplyTheme.typography.button16M,
                            textColor = if (isButtonEnabled) SolplyTheme.colors.white else SolplyTheme.colors.gray800,
                            enabledBackgroundColor = SolplyTheme.colors.gray900,
                            disabledBackgroundColor = SolplyTheme.colors.gray300
                        )
                    }
                }
            }
        }

        SolplyBasicButton(
            text = "다음",
            modifier = Modifier
                .padding(bottom = 24.dp),
            onClick = {
                if (isButtonEnabled) {
                    onNextClick()
                }
            },
            selected = isButtonEnabled,
            textStyle = SolplyTheme.typography.button16M,
            textColor = if (isButtonEnabled) SolplyTheme.colors.white else SolplyTheme.colors.gray800,
            enabledBackgroundColor = SolplyTheme.colors.gray900,
            disabledBackgroundColor = SolplyTheme.colors.gray300
        )
    }
}

@Composable
private fun LeftRegionPane(
    borderColor: Color,
    regions: List<ParentTownEntity>,
    selectedRegionId: Long?,
    onSelect: (Long) -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .width(124.dp)
            .fillMaxHeight()
            .background(SolplyTheme.colors.gray100)
    ) {
        itemsIndexed(items = regions) { index: Int, item: ParentTownEntity ->
        val selected = selectedRegionId == item.townId
            val bg = if (selected) SolplyTheme.colors.white else SolplyTheme.colors.gray100
            val textColor = if (selected) SolplyTheme.colors.black else SolplyTheme.colors.gray600
            val fontWeight = if (selected) FontWeight.SemiBold else FontWeight.Medium

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(46.dp)
                    .background(bg)
                    .customClickable(rippleEnabled = false) {
                        onSelect(item.townId)
                    }
                    .drawBehind {
                        val strokeWidth = 1.dp.toPx()
                        val w = size.width
                        val h = size.height

                        if (index == 0) {
                            drawLine(
                                color = borderColor,
                                start = Offset(0f, strokeWidth / 2),
                                end = Offset(w, strokeWidth / 2),
                                strokeWidth = strokeWidth
                            )
                        }

                        drawLine(
                            color = borderColor,
                            start = Offset(0f, h - strokeWidth / 2),
                            end = Offset(w, h - strokeWidth / 2),
                            strokeWidth = strokeWidth
                        )
                    },
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = item.townName,
                    style = SolplyTheme.typography.body14M.copy(fontWeight = fontWeight),
                    color = textColor
                )
            }
        }
    }
}

@Composable
private fun RightTownPane(
    borderColor: Color,
    towns: List<SubTownEntity>,
    selectedTownId: Long?,
    onSelect: (Long) -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        itemsIndexed(items = towns) { index, item ->
            val selected = item.townId == selectedTownId
            val textColor = if (selected) SolplyTheme.colors.black else SolplyTheme.colors.gray600
            val fontWeight = if (selected) FontWeight.SemiBold else FontWeight.Medium

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(46.dp)
                    .customClickable(rippleEnabled = false) { onSelect(item.townId) }
                    .drawBehind {
                        val strokeWidth = 1.dp.toPx()
                        val w = size.width
                        val h = size.height

                        if (index == 0) {
                            drawLine(
                                color = borderColor,
                                start = Offset(0f, strokeWidth / 2),
                                end = Offset(w, strokeWidth / 2),
                                strokeWidth = strokeWidth
                            )
                        }

                        drawLine(
                            color = borderColor,
                            start = Offset(0f, h - strokeWidth / 2),
                            end = Offset(w, h - strokeWidth / 2),
                            strokeWidth = strokeWidth
                        )
                    },
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = item.townName,
                    style = SolplyTheme.typography.body16M.copy(fontWeight = fontWeight),
                    color = textColor,
                    modifier = Modifier
                        .weight(1f)
                        .padding(start = 16.dp)
                )

                if (selected) {
                    Icon(
                        painter = painterResource(id = com.teamsolply.solply.designsystem.R.drawable.ic_select_icon),
                        contentDescription = "selected",
                        modifier = Modifier.padding(end = 16.dp),
                        tint = Color.Unspecified
                    )
                }
            }
        }
    }
}
