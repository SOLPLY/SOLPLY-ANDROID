package com.teamsolply.solply.maps

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.LayoutCoordinates
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInRoot
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.teamsolply.solply.maps.component.CourseItem
import com.teamsolply.solply.maps.extension.dragContainer
import com.teamsolply.solply.maps.extension.draggableItems
import com.teamsolply.solply.maps.interaction.rememberDragDropState
import com.teamsolply.solply.maps.model.CourseInfo
import com.teamsolply.solply.ui.extension.vibrate
import com.teamsolply.solply.ui.lifecycle.LaunchedEffectWithLifecycle
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged

@Composable
fun MapsRoute(
    paddingValues: PaddingValues,
    viewModel: MapsViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffectWithLifecycle {
        viewModel.sideEffect.collectLatest { sideEffect ->
            when (sideEffect) {
                else -> {}
            }
        }
    }
    
    MapsScreen(
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
        }
    )
}

@Composable
fun MapsScreen(
    course: List<CourseInfo>,
    removeIconVisible: Boolean,
    startCourseMove: (Boolean) -> Unit,
    moveCourse: (fromIndex: Int, toIndex: Int) -> Unit,
    removeCourse: (itemToRemove: Int) -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    var scrollToIndex by remember { mutableStateOf<Int?>(null) }
    val draggableItemSize by remember { derivedStateOf { course.size } }
    val lazyListState = rememberLazyListState()

    var isInRemoveIconArea by remember { mutableStateOf(false) }
    var removeIconBounds by remember { mutableStateOf<Rect?>(null) }
    var rootCoordinates: LayoutCoordinates? by remember { mutableStateOf(null) }
    var touchPositionInRoot by remember { mutableStateOf(Offset.Zero) }

    val dragDropState = rememberDragDropState(
        context = context,
        lazyListState = lazyListState,
        draggableItemsNum = draggableItemSize,
        onMove = { fromIndex, toIndex ->
            moveCourse(fromIndex, toIndex)
        },
        removeIconVisible = startCourseMove,
        onRemove = removeCourse,
        isInRemoveAreaProvider = { isInRemoveIconArea }
    )

    LaunchedEffect(scrollToIndex) {
        scrollToIndex?.let { index ->
            lazyListState.animateScrollToItem(index)
            scrollToIndex = null
        }
    }

    LaunchedEffect(Unit) {
        snapshotFlow { isInRemoveIconArea }
            .distinctUntilChanged()
            .collect { isInArea ->
                if (isInArea) {
                    context.vibrate(75)
                }
            }
    }

    Box(
        modifier = Modifier
            .onGloballyPositioned { coordinates ->
                rootCoordinates = coordinates
            }
            .pointerInput(Unit) {
                awaitPointerEventScope {
                    while (true) {
                        val event = awaitPointerEvent()
                        val change = event.changes.firstOrNull() ?: continue
                        val rootPosition = rootCoordinates?.localToRoot(change.position)
                        if (rootPosition != null && rootPosition != touchPositionInRoot) {
                            touchPositionInRoot = rootPosition
                            isInRemoveIconArea = removeIconBounds?.contains(rootPosition) == true
                        }
                    }
                }
            }
    ) {
        Column {
            LazyColumn(
                modifier = Modifier
                    .dragContainer(dragDropState)
                    .fillMaxSize(),
                state = lazyListState,
                contentPadding = PaddingValues(10.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                draggableItems(
                    items = course,
                    dragDropState = dragDropState
                ) { modifier, item ->
                    CourseItem(
                        modifier = modifier,
                        name = item.courseName,
                    )
                }
            }
        }
        if (removeIconVisible) {
            Icon(
                painter = painterResource(com.teamsolply.solply.designsystem.R.drawable.ic_bottom_nav_dummy),
                contentDescription = "remove",
                modifier = Modifier
                    .size(if (isInRemoveIconArea) 200.dp else 100.dp)
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 50.dp)
                    .onGloballyPositioned { coordinates ->
                        val positionInRoot = coordinates.positionInRoot()
                        val size = coordinates.size
                        removeIconBounds = Rect(
                            offset = positionInRoot,
                            size = Size(size.width.toFloat(), size.height.toFloat())
                        )
                    },
                tint = if (isInRemoveIconArea) Color.Green else Color.Gray
            )
        } else {
            removeIconBounds = null
        }
    }
}
