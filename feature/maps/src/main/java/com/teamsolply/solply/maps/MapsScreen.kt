package com.teamsolply.solply.maps

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.LayoutCoordinates
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInRoot
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.naver.maps.map.compose.ExperimentalNaverMapApi
import com.naver.maps.map.compose.NaverMap
import com.teamsolply.solply.designsystem.component.bottomsheet.SolplyBasicBottomSheet
import com.teamsolply.solply.designsystem.component.button.AddCourseButton
import com.teamsolply.solply.maps.component.MapsTopBar
import com.teamsolply.solply.maps.editcourse.DraggableCourse
import com.teamsolply.solply.maps.editcourse.interaction.rememberDragDropState
import com.teamsolply.solply.maps.model.CourseInfo
import com.teamsolply.solply.model.MapsType
import com.teamsolply.solply.ui.extension.vibrate
import com.teamsolply.solply.ui.lifecycle.LaunchedEffectWithLifecycle
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged

@Composable
fun MapsRoute(
    mapsType: MapsType,
    navigatePlaceDetail: () -> Unit,
    paddingValues: PaddingValues,
    viewModel: MapsViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffectWithLifecycle {
        viewModel.sideEffect.collectLatest { sideEffect ->
            when (sideEffect) {
                MapsSideEffect.DisabledRemoveCourse -> {
                    Toast.makeText(context, "Cannot delete course", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    MapsScreen(
        mapsType = mapsType,
        context = context,
        course = uiState.course,
        removeIconVisible = uiState.iconVisibility,
        startCourseMove = { iconVisibility ->
            viewModel.sendIntent(MapsIntent.StartCourseMove(iconVisibility = iconVisibility))
        },
        moveCourse = { from, to ->
            viewModel.sendIntent(MapsIntent.MoveCourseItem(fromIndex = from, toIndex = to))
        },
        removeCourse = { remove ->
            viewModel.sendIntent(MapsIntent.RemoveCourseItem(itemToRemove = remove))
        },
        navigatePlaceDetail = navigatePlaceDetail,
    )
}

@OptIn(ExperimentalNaverMapApi::class)
@Composable
fun MapsScreen(
    mapsType: MapsType,
    context: Context,
    course: List<CourseInfo>,
    removeIconVisible: Boolean,
    startCourseMove: (Boolean) -> Unit,
    moveCourse: (fromIndex: Int, toIndex: Int) -> Unit,
    removeCourse: (itemToRemove: Int) -> Unit,
    navigatePlaceDetail: () -> Unit,
    modifier: Modifier = Modifier
) {
    val scope = rememberCoroutineScope()
    var scrollToIndex by remember { mutableStateOf<Int?>(null) }
    val lazyListState = rememberLazyListState()

    val isInRemoveIconArea = remember { mutableStateOf(false) }
    var removeIconBounds by remember { mutableStateOf<Rect?>(null) }
    val draggableItemSize by remember { derivedStateOf { course.size } }
    val rootCoordinatesState = remember { mutableStateOf<LayoutCoordinates?>(null) }
    val touchPositionState = remember { mutableStateOf(Offset.Zero) }

    val dragDropState = rememberDragDropState(
        context = context,
        lazyListState = lazyListState,
        draggableItemsNum = draggableItemSize,
        onMove = { fromIndex, toIndex ->
            moveCourse(fromIndex, toIndex)
        },
        removeIconVisible = startCourseMove,
        onRemove = removeCourse,
        isInRemoveAreaProvider = { isInRemoveIconArea.value }
    )

    LaunchedEffect(scrollToIndex) {
        scrollToIndex?.let { index ->
            lazyListState.animateScrollToItem(index)
            scrollToIndex = null
        }
    }

    LaunchedEffect(Unit) {
        snapshotFlow { isInRemoveIconArea.value }
            .distinctUntilChanged()
            .collect { isInArea ->
                if (isInArea) {
                    context.vibrate(75)
                }
            }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            val topBarTitle = when (mapsType) {
                MapsType.ADD_COURSE -> "코스 상세보기"
                MapsType.EDIT_COURSE -> "수집함"
                else -> "장소 상세이름"
            }
            MapsTopBar(
                title = topBarTitle,
                onBackClick = { },
                onHomeClick = { navigatePlaceDetail() }
            )
            NaverMap(
                modifier = Modifier.fillMaxSize()
            )
        }

        SolplyBasicBottomSheet(
            modifier = Modifier.align(Alignment.BottomCenter),
            menuContent = {
                Row {
                    AddCourseButton(
                        onClick = {},
                        selected = true
                    )
                    //TODO("저장된 코스")
                }
            },
            content = {
                DraggableCourse(
                    course = course,
                    removeIconBounds = removeIconBounds,
                    isInRemoveIconArea = isInRemoveIconArea,
                    rootCoordinatesState = rootCoordinatesState,
                    touchPositionState = touchPositionState,
                    lazyListState = lazyListState,
                    dragDropState = dragDropState
                )
            }
        )

        Icon(
            painter = painterResource(com.teamsolply.solply.designsystem.R.drawable.ic_remove_floating),
            contentDescription = "remove",
            modifier = Modifier
                .size(if (isInRemoveIconArea.value) 200.dp else 100.dp)
                .align(Alignment.BottomCenter)
                .padding(bottom = 50.dp)
                .alpha(if (removeIconVisible) 1f else 0f)
                .onGloballyPositioned { coordinates ->
                    val positionInRoot = coordinates.positionInRoot()
                    val size = coordinates.size
                    removeIconBounds = Rect(
                        offset = positionInRoot,
                        size = Size(size.width.toFloat(), size.height.toFloat())
                    )
                },
            tint = Color.Unspecified
        )
    }
}
