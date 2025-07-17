package com.teamsolply.solply.course.favoriteTown

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.teamsolply.solply.course.favoriteTown.model.CourseState
import com.teamsolply.solply.course.component.FavoriteTownTopBar
import com.teamsolply.solply.course.favoriteTown.model.TownModel
import com.teamsolply.solply.designsystem.component.button.AddLocalAreaButton
import com.teamsolply.solply.designsystem.component.button.SolplyBasicButton
import com.teamsolply.solply.designsystem.theme.SolplyTheme

@Composable
fun FavoriteTownRoute(
    paddingValues: PaddingValues,
    onBoardingIntent: (FavoriteTownIntent) -> Unit,
    navigateToBack: () -> Unit,
    viewModel: FavoriteViewModel = hiltViewModel()
) {
    val state = viewModel.uiState.collectAsState().value

    val selectedTown = state.townInfo.selectedTown

    FavoriteTownScreen(
        state = CourseState(
            townList = state.townInfo.favoriteTownList.map {
                TownModel(id = it.townId, name = it.townName)
            },
            selectedTownId = selectedTown?.townId
        ),
        onBoardingIntent = { intent -> viewModel.handleIntent(intent) },
        onNextClick = navigateToBack,
        onReturnToHomeClick = navigateToBack,
        onBackButtonClick = navigateToBack,
    )
}


@Composable
fun FavoriteTownScreen(
    state: CourseState,
    onNextClick: () -> Unit,
    onBoardingIntent: (FavoriteTownIntent) -> Unit,
    onBackButtonClick: () -> Unit,
    onReturnToHomeClick: () -> Unit
) {
    val townList = state.townList

    val isButtonEnabled = state.selectedTownId != null

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 14.dp)
    ) {
        FavoriteTownTopBar(
            onBackButtonClick = { onBackButtonClick() },
            onReturnToHomeButtonClick = { onReturnToHomeClick() }
        )

        Column(
            modifier = Modifier
                .weight(1f)
                .padding(top = 32.dp),
            verticalArrangement = Arrangement.Top
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp, Alignment.CenterHorizontally)
            ) {
                townList.forEach { town ->
                    AddLocalAreaButton(
                        text = town.name,
                        onClick = {
                            onBoardingIntent(FavoriteTownIntent.SelectTown(town.id))
                        },
                        isShowMore = false,
                        selected = state.selectedTownId == town.id
                    )
                }

                AddLocalAreaButton(
                    text = "",
                    onClick = { },
                    isShowMore = true,
                    selected = false
                )
            }
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

@Preview(showBackground = true, name = "FavoriteTownScreen Preview")
@Composable
fun FavoriteTownScreenPreview() {
    // 예시용 TownModel 리스트
    val fakeTownList = listOf(
        TownModel(id = 1, name = "망원동"),
        TownModel(id = 2, name = "연남동")
    )

    SolplyTheme {
        FavoriteTownScreen(
            state = CourseState(
                townList = fakeTownList,
                selectedTownId = 2 // 연남동 선택됨
            ),
            onNextClick = {}, // no-op
            onBoardingIntent = {}, // no-op
            onBackButtonClick = {}, // no-op
            onReturnToHomeClick = {} // no-op
        )
    }
}
