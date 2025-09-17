package com.teamsolply.solply.course.favoriteTown

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.teamsolply.solply.course.component.FavoriteTownTopBar
import com.teamsolply.solply.course.favoriteTown.model.CourseState
import com.teamsolply.solply.course.favoriteTown.model.Region
import com.teamsolply.solply.course.favoriteTown.model.TownLite
import com.teamsolply.solply.designsystem.component.button.SolplyBasicButton
import com.teamsolply.solply.designsystem.theme.SolplyTheme
@Composable
fun FavoriteTownRoute(
    paddingValues: PaddingValues,
    onBoardingIntent: (FavoriteTownIntent) -> Unit,
    navigateToBack: () -> Unit,
    viewModel: FavoriteViewModel = hiltViewModel()
) {
    val s = viewModel.uiState.collectAsState().value

    val courseState = CourseState(
        selectedTownId = s.selectedTownId ?: s.townInfo?.selectedTown?.townId,
        selectedRegionId = s.selectedRegionId,
        regions = s.regions,
        townsByRegion = s.townsByRegion
    )

    FavoriteTownScreen(
        state = courseState,
        onBoardingIntent = { intent -> viewModel.handleIntent(intent) },
        onNextClick = navigateToBack,
        onReturnToHomeClick = navigateToBack,
        onBackButtonClick = navigateToBack,
        modifier = Modifier.padding(paddingValues)
    )
}

@Composable
fun FavoriteTownScreen(
    state: CourseState,
    onNextClick: () -> Unit,
    onBoardingIntent: (FavoriteTownIntent) -> Unit,
    onBackButtonClick: () -> Unit,
    onReturnToHomeClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val isButtonEnabled = state.selectedTownId != null
    val regions = state.regions
    val towns = state.townsByRegion[state.selectedRegionId] ?: emptyList()

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(top = 14.dp)
    ) {
        FavoriteTownTopBar(
            onBackButtonClick = onBackButtonClick,
            onReturnToHomeButtonClick = onReturnToHomeClick
        )

        Row(
            modifier = Modifier
                .weight(1f)
                .padding(top = 16.dp)
        ) {
            LeftRegionPane(
                regions = regions,
                selectedRegionId = state.selectedRegionId,
                onSelect = { id -> onBoardingIntent(FavoriteTownIntent.SelectRegion(id)) }
            )

            VerticalDivider(
                modifier = Modifier.fillMaxHeight(),
                color = SolplyTheme.colors.gray200,
                thickness = 1.dp
            )

            RightTownPane(
                towns = towns,
                selectedTownId = state.selectedTownId,
                onSelect = { id -> onBoardingIntent(FavoriteTownIntent.SelectTown(id)) }
            )
        }

        SolplyBasicButton(
            text = "완료",
            modifier = Modifier
                .padding(bottom = 24.dp, start = 20.dp, end = 20.dp),
            onClick = {
                if (isButtonEnabled) {
                    onBoardingIntent(FavoriteTownIntent.ConfirmSelection)
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
    regions: List<Region>,
    selectedRegionId: Long?,
    onSelect: (Long) -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .width(124.dp)
            .fillMaxHeight()
            .background(SolplyTheme.colors.gray200)
    ) {
        items(regions, key = { it.id }) { region ->
            val selected = region.id == selectedRegionId
            val bg = if (selected) Color.White else Color.Transparent
            val textColor = if (selected) SolplyTheme.colors.black else SolplyTheme.colors.gray600
            val fontWeight = if (selected) FontWeight.SemiBold else FontWeight.Medium

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
                    .background(bg)
                    .clickable { onSelect(region.id) }
                    .padding(horizontal = 16.dp),
                contentAlignment = Alignment.CenterStart
            ) {
                Text(
                    text = region.name,
                    style = SolplyTheme.typography.body14M.copy(fontWeight = fontWeight),
                    color = textColor
                )
            }

            HorizontalDivider(
                modifier = Modifier.fillMaxWidth(),
                thickness = 1.dp,
                color = SolplyTheme.colors.gray200
            )
        }
    }
}

@Composable
private fun RightTownPane(
    towns: List<TownLite>,
    selectedTownId: Long?,
    onSelect: (Long) -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 16.dp)
    ) {
        items(towns, key = { it.id }) { town ->
            val selected = town.id == selectedTownId
            val textColor = if (selected) SolplyTheme.colors.black else SolplyTheme.colors.gray600
            val fontWeight = if (selected) FontWeight.SemiBold else FontWeight.Medium

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(46.dp)
                    .clickable { onSelect(town.id) }
                    .padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = town.name,
                    style = SolplyTheme.typography.body16M.copy(fontWeight = fontWeight),
                    color = textColor,
                    modifier = Modifier.weight(1f)
                )

                if (selected) {
                    Icon(
                        painter = painterResource(id = com.teamsolply.solply.designsystem.R.drawable.ic_select_icon),
                        contentDescription = "selected",
                        tint = Color.Unspecified
                    )
                }
            }
        }
    }
}
