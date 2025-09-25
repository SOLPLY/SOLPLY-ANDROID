package com.teamsolply.solply.course

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.teamsolply.solply.designsystem.component.card.SolplyCourseCard
import com.teamsolply.solply.designsystem.component.header.SolplyHomeHeader
import com.teamsolply.solply.designsystem.theme.SolplyTheme
import com.teamsolply.solply.model.MapsType
import com.teamsolply.solply.search.SearchDialog
import com.teamsolply.solply.ui.lifecycle.LaunchedEffectWithLifecycle
import kotlinx.coroutines.flow.collectLatest

@Composable
fun CourseRoute(
    paddingValues: PaddingValues,
    navigateToMaps: (String, Long, Long) -> Unit,
    navigateToTownSelect: () -> Unit,
    viewModel: CourseViewModel = hiltViewModel()
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.sendIntent(CourseIntent.Init)
    }

    LaunchedEffectWithLifecycle {
        viewModel.sideEffect.collectLatest { sideEffect ->
            when (sideEffect) {
                is CourseSideEffect.NavigateToCourseMap -> {
                    navigateToMaps(
                        MapsType.ADD_COURSE.name,
                        state.user.selectedTown.townId,
                        sideEffect.courseId
                    )
                }

                is CourseSideEffect.NavigateToPlaceDetail -> {
                    navigateToMaps(
                        MapsType.PLACE_DETAIL.name,
                        sideEffect.townId,
                        sideEffect.placeId
                    )
                }
            }
        }
    }

    CourseScreen(
        state = state,
        navigateToMaps = { courseId ->
            viewModel.sendIntent(CourseIntent.CourseCardClick(courseId = courseId))
        },
        navigateToTownSelect = navigateToTownSelect,
        changeSearchDialogVisibility = { visible ->
            viewModel.sendIntent(CourseIntent.ChangeSearchDialogVisibility(visible = visible))
        },
        modifier = Modifier.padding(paddingValues)
    )

    if (state.isSearchDialogVisible) {
        SearchDialog(
            onDismissRequest = {
                viewModel.sendIntent(CourseIntent.ChangeSearchDialogVisibility(visible = false))
            },
            navigateToPlaceDetail = { placeId, townId ->
                //viewModel.sendIntent(CourseIntent.PlaceClicked(placeId = placeId, townId = townId))
            },
            navigateToRegisterPlace = {
                //TODO. 장소 등록하기
            }
        )
    }
}

@Composable
fun CourseScreen(
    state: CourseState,
    navigateToMaps: (Long) -> Unit,
    navigateToTownSelect: () -> Unit,
    changeSearchDialogVisibility: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    val courseList = state.courseList
    val user = state.user
    val recommendText = state.recommendText
    val gridState = rememberLazyGridState()

    Column(modifier = modifier) {
        SolplyHomeHeader(
            townName = user.selectedTown.townName,
            modifier = Modifier
                .padding(bottom = 8.dp),
            onClickTownName = { navigateToTownSelect() },
            changeSearchDialogVisibility = changeSearchDialogVisibility
        )

        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            state = gridState,
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 16.dp, end = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            item(span = { GridItemSpan(maxLineSpan) }) {
                Text(
                    text = recommendText,
                    style = SolplyTheme.typography.display20Sb,
                    color = SolplyTheme.colors.black,
                    modifier = Modifier
                        .padding(start = 4.dp, top = 16.dp, bottom = 9.dp)
                )
            }

            items(courseList) { course ->
                SolplyCourseCard(
                    title = course.courseName,
                    imgRes = course.imageUrl,
                    placeType = course.tagList,
                    savedCourse = course.isSaved,
                    modifier = Modifier
                        .fillMaxWidth(),
                    onClick = {
                        navigateToMaps(course.courseId)
                    }
                )
            }
            item(span = { GridItemSpan(2) }) {
                Spacer(modifier = Modifier.height(60.dp))
            }
        }
    }
}
