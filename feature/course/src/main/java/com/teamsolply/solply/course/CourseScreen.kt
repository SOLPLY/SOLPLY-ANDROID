package com.teamsolply.solply.course

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.teamsolply.solply.designsystem.component.card.SolplyCourseCard
import com.teamsolply.solply.designsystem.component.header.CourseHeader
import com.teamsolply.solply.designsystem.theme.SolplyTheme
import com.teamsolply.solply.model.PlaceType
import com.teamsolply.solply.ui.extension.customClickable

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
    val recommendText = state.recommendText
    val gridState = rememberLazyGridState()

    Column {
        CourseHeader(
            townName = user.favoriteTowns,
            modifier = Modifier
                .padding(start = 16.dp, end = 16.dp)
        )

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
}

@Preview(showBackground = true)
@Composable
fun CourseScreenPreview() {
    SolplyTheme {
    }
}
