package com.teamsolply.solply.course.favoriteTown

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
    onBoardingIntent: () -> Unit,
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
            //selectedTownId = state.townInfo.selectedTown.townId
        ),
        onBoardingIntent = onBoardingIntent,
        onNextClick = navigateToBack,
        onReturnToHomeClick = navigateToBack,
        onBackButtonClick = navigateToBack,
    )
}


@Composable
fun FavoriteTownScreen(
    state: CourseState,
    onNextClick: () -> Unit,
    onBoardingIntent: () -> Unit,
    onBackButtonClick: () -> Unit,
    onReturnToHomeClick: () -> Unit
) {
    val townList = state.townList

    val isButtonEnabled = state.selectedTownId != null

    LaunchedEffect(Unit) {
        Log.d("FavoriteTownScreen", "townList: $townList")
        Log.d("FavoriteTownScreen", "selectedTownId: ${state.selectedTownId}")
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 20.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column(
            modifier = Modifier
        ) {
            FavoriteTownTopBar(
                onBackButtonClick = { onBackButtonClick() },
                onReturnToHomeButtonClick = { onReturnToHomeClick() }
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 28.dp),
                horizontalArrangement = Arrangement.spacedBy(18.dp, Alignment.CenterHorizontally)
            ) {
                townList.forEach { town ->
                    AddLocalAreaButton(
                        text = town.name,
                        onClick = { onBoardingIntent() },
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