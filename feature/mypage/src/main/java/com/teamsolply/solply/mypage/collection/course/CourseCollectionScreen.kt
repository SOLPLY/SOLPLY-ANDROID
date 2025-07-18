package com.teamsolply.solply.mypage.collection.course

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.teamsolply.solply.designsystem.component.card.SolplyCourseCard
import com.teamsolply.solply.model.MapsType
import com.teamsolply.solply.mypage.R
import com.teamsolply.solply.mypage.collection.component.CollectionScreen
import com.teamsolply.solply.mypage.collection.component.SelectModeBar
import com.teamsolply.solply.ui.extension.customClickable
import com.teamsolply.solply.ui.lifecycle.LaunchedEffectWithLifecycle
import kotlinx.coroutines.flow.collectLatest

@Composable
fun CourseCollectionRoute(
    paddingValues: PaddingValues,
    townId: Long,
    townName: String,
    navigateToMaps: (String, Long, Long) -> Unit,
    navigateToBack: () -> Unit,
    viewModel: CourseCollectionViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val selectText =
        if (uiState.selectMode) stringResource(R.string.mypage_delete) else stringResource(R.string.mypage_select)
    val cancelText = if (uiState.selectMode) stringResource(R.string.mypage_cancel) else ""

    LaunchedEffect(Unit) {
        viewModel.sendIntent(CourseCollectionIntent.Init(townId, townName))
    }

    LaunchedEffectWithLifecycle {
        viewModel.sideEffect.collectLatest { sideEffect ->
            when (sideEffect) {
                CourseCollectionSideEffect.NavigateToBack -> navigateToBack()
                is CourseCollectionSideEffect.NavigateToMap -> navigateToMaps(
                    MapsType.EDIT_COURSE.name,
                    uiState.townId,
                    sideEffect.courseId
                )
            }
        }
    }

    CollectionScreen(
        town = uiState.townName,
        onBackButtonClick = { viewModel.sendIntent(CourseCollectionIntent.BackButtonClick) },
        onDialogConfirmClick = { viewModel.sendIntent(CourseCollectionIntent.DialogConfirmClick) },
        onDialogDismissClick = { viewModel.sendIntent(CourseCollectionIntent.DialogDismissClick) },
        dialogState = uiState.dialogState,
        content = {
            item(span = { GridItemSpan(maxLineSpan) }) {
                SelectModeBar(
                    selectMode = uiState.selectMode,
                    onSelectButtonClick = { viewModel.sendIntent(CourseCollectionIntent.SelectButtonClick) },
                    onDeleteButtonClick = { viewModel.sendIntent(CourseCollectionIntent.DeleteButtonClick) },
                    onCancelButtonClick = { viewModel.sendIntent(CourseCollectionIntent.CancelButtonClick) }
                )
            }
            itemsIndexed(uiState.courses) { index, it ->
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .customClickable(
                            rippleEnabled = false
                        ) {
                            viewModel.sendIntent(
                                CourseCollectionIntent.CourseCardClick(
                                    it.courseId,
                                    index
                                )
                            )
                        },
                    contentAlignment = if (index % 2 == 0) {
                        Alignment.CenterEnd
                    } else {
                        Alignment.CenterStart
                    }
                ) {
                    SolplyCourseCard(
                        title = it.courseName,
                        placeType = it.placeTypeList,
                        imgRes = it.imageUrls.first(),
                        selected = it.isSelected,
                        onClick = {
                            viewModel.sendIntent(
                                CourseCollectionIntent.CourseCardClick(
                                    it.courseId,
                                    index
                                )
                            )
                        },
                        modifier =
                        if (index % 2 == 0) {
                            Modifier.padding(end = 5.dp)
                        } else {
                            Modifier.padding(start = 5.dp)
                        },
                        savedCourse = it.isSaved
                    )
                }
            }
            item(span = { GridItemSpan(2) }) {
                Spacer(modifier = Modifier.height(60.dp))
            }
        },
        modifier = Modifier.padding(paddingValues)
    )
}
