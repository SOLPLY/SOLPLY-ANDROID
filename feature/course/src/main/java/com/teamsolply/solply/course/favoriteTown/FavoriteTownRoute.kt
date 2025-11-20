package com.teamsolply.solply.course.favoriteTown

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.teamsolply.solply.course.component.FavoriteTownTopBar
import com.teamsolply.solply.course.favoriteTown.model.Region
import com.teamsolply.solply.course.favoriteTown.model.TownLite
import com.teamsolply.solply.designsystem.component.button.SolplyBasicButton
import com.teamsolply.solply.designsystem.theme.SolplyTheme
import com.teamsolply.solply.ui.extension.customClickable

@Composable
fun FavoriteTownRoute(
    paddingValues: PaddingValues,
    selectedTownId: Long,
    navigateToBack: (Long?) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: FavoriteViewModel = hiltViewModel()
) {
    val state = viewModel.uiState.collectAsState().value
    val isButtonEnabled = state.selectedTownId != null
    val regions = state.regions
    val towns = state.townsByRegion[state.selectedRegionId] ?: emptyList()
    val handleDismiss = {
        navigateToBack(state.selectedTownId)
    }

    LaunchedEffect(Unit) {
        viewModel.sideEffect.collect { sideEffect ->
            when (sideEffect) {
                is FavoriteTownSideEffect.DismissWithResult -> navigateToBack(sideEffect.selectedTownId)
            }
        }
    }

    LaunchedEffect(Unit) {
        viewModel.sendIntent((FavoriteTownIntent.LoadFavoriteTownList(selectedTownId = selectedTownId)))
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(paddingValues)
    ) {
        FavoriteTownTopBar(
            onBackButtonClick = handleDismiss,
            onReturnToHomeButtonClick = handleDismiss
        )
        Row(
            modifier = Modifier
                .weight(1f)
                .padding(top = 16.dp)
                .background(SolplyTheme.colors.white)
        ) {
            LeftRegionPane(
                regions = regions,
                selectedRegionId = state.selectedRegionId,
                onSelect = { id -> viewModel.sendIntent(FavoriteTownIntent.SelectRegion(id)) }
            )

            VerticalDivider(
                modifier = Modifier.fillMaxHeight(),
                color = SolplyTheme.colors.gray100,
                thickness = 1.dp
            )

            RightTownPane(
                towns = towns,
                selectedTownId = state.selectedTownId,
                onSelect = { id -> viewModel.sendIntent(FavoriteTownIntent.SelectTown(id)) }
            )
        }

        SolplyBasicButton(
            text = "완료",
            modifier = Modifier
                .padding(bottom = 24.dp, start = 20.dp, end = 20.dp),
            onClick = { viewModel.sendIntent(FavoriteTownIntent.ConfirmSelection) },
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
    regions: List<Region>,
    selectedRegionId: Long?,
    onSelect: (Long) -> Unit
) {
    val borderColor = SolplyTheme.colors.gray200

    LazyColumn(
        modifier = Modifier
            .width(124.dp)
            .fillMaxHeight()
            .background(SolplyTheme.colors.gray100)
    ) {
        items(regions, key = { it.id }) { region ->
            val selected = region.id == selectedRegionId
            val bg = if (selected) SolplyTheme.colors.white else SolplyTheme.colors.gray100
            val textColor =
                if (selected) SolplyTheme.colors.black else SolplyTheme.colors.gray600

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(46.dp)
                    .background(bg)
                    .customClickable(rippleEnabled = false) {
                        onSelect(region.id)
                    }
                    .drawBehind {
                        val strokeWidth = 1.dp.toPx()
                        val w = size.width
                        val h = size.height

                        if (region == regions.firstOrNull()) {
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
                    text = region.name,
                    style = SolplyTheme.typography.body16M,
                    color = textColor
                )
            }
        }
    }
}

@Composable
private fun RightTownPane(
    towns: List<TownLite>,
    selectedTownId: Long?,
    onSelect: (Long) -> Unit
) {
    val borderColor = SolplyTheme.colors.gray200

    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .background(SolplyTheme.colors.white)
    ) {
        items(towns, key = { it.id }) { town ->
            val selected = town.id == selectedTownId
            val textColor =
                if (selected) SolplyTheme.colors.black else SolplyTheme.colors.gray600

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(46.dp)
                    .customClickable(rippleEnabled = false) { onSelect(town.id) }
                    .drawBehind {
                        val strokeWidth = 1.dp.toPx()
                        val w = size.width
                        val h = size.height

                        if (town == towns.firstOrNull()) {
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
                    text = town.name,
                    style = SolplyTheme.typography.body16R,
                    color = textColor,
                    modifier = Modifier
                        .weight(1f)
                        .padding(start = 16.dp)
                )

                if (selected) {
                    Icon(
                        painter = painterResource(
                            id = com.teamsolply.solply.designsystem.R.drawable.ic_select_icon
                        ),
                        contentDescription = "selected",
                        modifier = Modifier.padding(end = 16.dp),
                        tint = Color.Unspecified
                    )
                }
            }
        }
    }
}
