package com.teamsolply.solply.course

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.teamsolply.solply.model.MapsType
import com.teamsolply.solply.ui.extension.customClickable

@Composable
fun CourseRoute(
    paddingValues: PaddingValues,
    navigateToMaps: (String) -> Unit,
    viewModel: CourseViewModel = hiltViewModel()
) {
    CourseScreen(
        navigateToMaps = navigateToMaps
    )
}

@Composable
fun CourseScreen(
    navigateToMaps: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Course",
            modifier = Modifier.customClickable { navigateToMaps(MapsType.ADD_COURSE.name) }
        )
    }
}
