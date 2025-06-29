package com.teamsolply.solply.maps.bottomsheet.course

import android.content.Context
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.SheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.teamsolply.solply.designsystem.component.bottomsheet.BasicBottomSheet
import com.teamsolply.solply.maps.bottomsheet.course.extension.dragContainer
import com.teamsolply.solply.maps.bottomsheet.course.extension.draggableItems
import com.teamsolply.solply.maps.bottomsheet.course.interaction.rememberDragDropState
import com.teamsolply.solply.maps.component.CourseItem
import com.teamsolply.solply.maps.model.CourseInfo

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CourseBottomSheet(
    course: List<CourseInfo>,
    context: Context,
    removeIconVisible: Boolean,
    isInRemoveIconArea: MutableState<Boolean>,
    lazyListState: LazyListState,
    startCourseMove: (Boolean) -> Unit,
    moveCourse: (fromIndex: Int, toIndex: Int) -> Unit,
    removeCourse: (itemToRemove: Int) -> Unit,
    onDismissRequest: () -> Unit,
    modifier: Modifier = Modifier,
    sheetState: SheetState
) {
    val sheetHeightFraction = 0.40f
    val screenHeight = LocalConfiguration.current.screenHeightDp.dp
    val targetHeight = screenHeight * sheetHeightFraction

    val draggableItemSize by remember { derivedStateOf { course.size } }
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
        isInRemoveAreaProvider = { isInRemoveIconArea.value }
    )

    if (sheetState.isVisible) {
        BasicBottomSheet(
            onDismissRequest = onDismissRequest,
            sheetState = sheetState
        ) {
            Box(
                modifier = Modifier
                    .height(targetHeight)
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
                                    isInRemoveIconArea.value =
                                        removeIconBounds?.contains(rootPosition) == true
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
                            .size(if (isInRemoveIconArea.value) 200.dp else 100.dp)
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
                        tint = if (isInRemoveIconArea.value) Color.Green else Color.Gray
                    )
                } else {
                    removeIconBounds = null
                }
            }
        }
    }
}