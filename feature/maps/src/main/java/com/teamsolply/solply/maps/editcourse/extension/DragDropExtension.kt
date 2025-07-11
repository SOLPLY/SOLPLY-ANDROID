package com.teamsolply.solply.maps.editcourse.extension

import android.annotation.SuppressLint
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.gestures.detectDragGesturesAfterLongPress
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.zIndex
import com.teamsolply.solply.maps.editcourse.interaction.DragDropState
import com.teamsolply.solply.maps.model.DraggableItem

inline fun <T : Any> LazyListScope.draggableItems(
    items: List<T>,
    dragDropState: DragDropState,
    noinline key: ((index: Int, item: T) -> Any)? = null,
    crossinline content: @Composable (index: Int, Modifier, T) -> Unit
) {
    itemsIndexed(
        items = items,
        key = if (key != null) { i, item -> key(i, item) } else null,
        contentType = { index, _ -> DraggableItem(index = index) }
    ) { index, item ->
        val modifier = if (dragDropState.draggingItemIndex == index) {
            Modifier
                .zIndex(1f)
                .graphicsLayer {
                    translationY = dragDropState.deltaY
                    translationX = dragDropState.deltaX
                }
        } else {
            Modifier.animateItem(
                fadeInSpec = null,
                fadeOutSpec = null,
                placementSpec = tween(
                    durationMillis = 200,
                    easing = FastOutSlowInEasing
                )
            )
        }
        content(index, modifier, item)
    }
}

@SuppressLint("SuspiciousModifierThen")
fun Modifier.dragContainer(dragDropState: DragDropState): Modifier {
    return this.then(
        pointerInput(dragDropState) {
            detectDragGesturesAfterLongPress(
                onDrag = { change, offset ->
                    change.consume()
                    dragDropState.onDrag(offset = offset)
                },
                onDragStart = { offset ->
                    dragDropState.onDragStart(offset)
                },
                onDragEnd = {
                    dragDropState.onDragInterrupted()
                },
                onDragCancel = {
                    dragDropState.onDragInterrupted()
                }
            )
        }
    )
}
