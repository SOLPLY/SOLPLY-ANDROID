package com.teamsolply.solply.mypage.collection.course

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.teamsolply.solply.mypage.model.PlaceCard

@Composable
fun CourseCollectionRoute(
    paddingValues: PaddingValues,
    navigateToMaps: (String) -> Unit,
    navigateToBack: () -> Unit,
    viewModel: CourseCollectionViewModel = hiltViewModel()
) {
}

@Composable
fun CourseCollectionScreen(
    town: String,
    course: List<PlaceCard>,
    onBackButtonClick: () -> Unit,
    onSelectButtonClick: () -> Unit,
    onDeleteButtonClick: () -> Unit,
    onCancelButtonClick: () -> Unit,
    onCourseClick: (Int, Int) -> Unit,
    onDialogConfirmClick: () -> Unit,
    onDialogDismissClick: () -> Unit,
    isSelectMode: Boolean,
    dialogState: Boolean,
    modifier: Modifier = Modifier
) {
}
