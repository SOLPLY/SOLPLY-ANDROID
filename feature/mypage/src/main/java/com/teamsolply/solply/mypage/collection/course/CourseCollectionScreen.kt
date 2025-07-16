package com.teamsolply.solply.mypage.collection.course

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.teamsolply.solply.designsystem.component.card.SolplyCourseCard
import com.teamsolply.solply.designsystem.theme.SolplyTheme
import com.teamsolply.solply.model.MapsType
import com.teamsolply.solply.model.PlaceType
import com.teamsolply.solply.mypage.collection.component.CollectionScreen
import com.teamsolply.solply.ui.extension.customClickable
import com.teamsolply.solply.ui.lifecycle.LaunchedEffectWithLifecycle
import kotlinx.coroutines.flow.collectLatest

@Composable
fun CourseCollectionRoute(
    paddingValues: PaddingValues,
    townId: Int,
    townName: String,
    navigateToMaps: (String) -> Unit,
    navigateToBack: () -> Unit,
    viewModel: CourseCollectionViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.sendIntent(CourseCollectionIntent.Init(townId, townName))
    }

    LaunchedEffectWithLifecycle {
        viewModel.sideEffect.collectLatest { sideEffect ->
            when (sideEffect) {
                CourseCollectionSideEffect.NavigateToBack -> navigateToBack()
                CourseCollectionSideEffect.NavigateToMap -> navigateToMaps(MapsType.EDIT_COURSE.name)
            }
        }
    }

    CollectionScreen(
        town = uiState.townName,
        onBackButtonClick = { viewModel.sendIntent(CourseCollectionIntent.BackButtonClick) },
        onSelectButtonClick = { viewModel.sendIntent(CourseCollectionIntent.SelectButtonClick) },
        onDeleteButtonClick = { viewModel.sendIntent(CourseCollectionIntent.DeleteButtonClick) },
        onCancelButtonClick = { viewModel.sendIntent(CourseCollectionIntent.CancelButtonClick) },
        onDialogConfirmClick = { viewModel.sendIntent(CourseCollectionIntent.DialogConfirmClick) },
        onDialogDismissClick = { viewModel.sendIntent(CourseCollectionIntent.DialogDismissClick) },
        isSelectMode = uiState.selectMode,
        dialogState = uiState.dialogState,
        content = {
            itemsIndexed(uiState.courses) { index, it ->
                val iconColor = when (it.placeTypeList[0]) {
                    PlaceType.BOOKSTORE, PlaceType.SHOPPING -> SolplyTheme.colors.purple400
                    PlaceType.FOOD -> SolplyTheme.colors.yellow300
                    PlaceType.CAFE -> SolplyTheme.colors.red500
                    else -> SolplyTheme.colors.green400
                }
                val iconBackgroundColor = when (it.placeTypeList[0]) {
                    PlaceType.BOOKSTORE, PlaceType.SHOPPING -> SolplyTheme.colors.purple100
                    PlaceType.FOOD -> SolplyTheme.colors.yellow100
                    PlaceType.CAFE -> SolplyTheme.colors.red200
                    else -> SolplyTheme.colors.green200
                }
                val backgroundColor = when (it.placeTypeList[0]) {
                    PlaceType.BOOKSTORE, PlaceType.SHOPPING -> SolplyTheme.colors.purple300
                    PlaceType.FOOD -> SolplyTheme.colors.yellow200
                    PlaceType.CAFE -> SolplyTheme.colors.red300
                    else -> SolplyTheme.colors.green300
                }
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .customClickable(
                            rippleEnabled = false
                        ) {
                            Log.d("tq", "tlqkf")
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
                        imgRes = com.teamsolply.solply.designsystem.R.drawable.img_course_dummy,
                        selected = it.isSelected,
                        iconColor = iconColor,
                        iconBackGroundColor = iconBackgroundColor,
                        backgroundColor = backgroundColor,
                        onClick = {
                            Log.d("tq", "tlqkf")
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
                        savedPlace = true,
                        savedCourse = true
                    )
                }
            }
        },
        modifier = Modifier.padding(paddingValues)
    )
}
