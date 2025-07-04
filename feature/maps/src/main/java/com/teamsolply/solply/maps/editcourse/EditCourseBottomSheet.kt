package com.teamsolply.solply.maps.editcourse

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.LayoutCoordinates
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.unit.dp
import com.teamsolply.solply.maps.component.CourseItem
import com.teamsolply.solply.maps.editcourse.extension.dragContainer
import com.teamsolply.solply.maps.editcourse.extension.draggableItems
import com.teamsolply.solply.maps.editcourse.interaction.DragDropState
import com.teamsolply.solply.maps.model.CourseInfo

@Composable
fun EditCourseBottomSheet(
    course: List<CourseInfo>,
    removeIconBounds: Rect?,
    isInRemoveIconArea: MutableState<Boolean>,
    rootCoordinatesState: MutableState<LayoutCoordinates?>,
    touchPositionState: MutableState<Offset>,
    lazyListState: LazyListState,
    dragDropState: DragDropState
) {
    Box(
        modifier = Modifier
            .onGloballyPositioned { coordinates ->
                rootCoordinatesState.value = coordinates
            }
            .pointerInput(Unit) {
                awaitPointerEventScope {
                    while (true) {
                        val event = awaitPointerEvent()
                        val change = event.changes.firstOrNull() ?: continue
                        val rootPosition = rootCoordinatesState.value?.localToRoot(change.position)
                        if (rootPosition != null && rootPosition != touchPositionState.value) {
                            touchPositionState.value = rootPosition
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
                        name = item.courseName
                    )
                }
            }
        }
    }
}
