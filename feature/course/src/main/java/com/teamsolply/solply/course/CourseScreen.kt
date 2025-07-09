package com.teamsolply.solply.course

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.teamsolply.solply.designsystem.theme.SolplyTheme
import com.teamsolply.solply.ui.extension.customClickable
import com.teamsolply.solply.designsystem.component.card.SolplyCourseCard
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.runtime.getValue
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.teamsolply.solply.designsystem.component.header.CourseHeader
import com.teamsolply.solply.model.PlaceType

@Composable
fun CourseRoute(
    paddingValues: PaddingValues,
    navigateToMaps: (String) -> Unit,
    viewModel: CourseViewModel = hiltViewModel()
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    CourseScreen(
        state = state,
        navigateToMaps = navigateToMaps,
        modifier = Modifier.padding(paddingValues)
    )
}


@Composable
fun CourseScreen(
    state: CourseState,
    navigateToMaps: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val courseList = state.courseList
    val user = state.user
    val gridState = rememberLazyGridState()

    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        state = gridState,
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item(span = { GridItemSpan(maxLineSpan) }) {
            CourseHeader(
                townName = user.favoriteTowns,
                persona = user.persona,
                nickname = user.nickname
            )
        }

        items(courseList) { course ->
            SolplyCourseCard(
                title = course.title,
                imgRes = com.teamsolply.solply.designsystem.R.drawable.img_course_dummy,
                placeType = course.mainTags.map { PlaceType.valueOf(it) },
                backgroundColor = SolplyTheme.colors.red300,
                iconColor = SolplyTheme.colors.red500,
                iconBackGroundColor = SolplyTheme.colors.red200,
                savedCourse = course.isBookmarked,
                modifier = Modifier
                    .fillMaxWidth()
                    .customClickable {
                        navigateToMaps("")
                    }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CourseScreenPreview() {
    SolplyTheme {
    }
}