package com.teamsolply.solply.maps.editcourse.interaction

import android.content.Context
import androidx.compose.foundation.gestures.scrollBy
import androidx.compose.foundation.lazy.LazyListItemInfo
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.geometry.Offset
import com.teamsolply.solply.ui.extension.vibrate
import kotlinx.coroutines.channels.Channel

@Composable
fun rememberDragDropState(
    context: Context,
    lazyListState: LazyListState,
    onMove: (Int, Int) -> Unit,
    draggableItemsNum: Int,
    removeIconVisible: (Boolean) -> Unit,
    onRemove: (Int) -> Unit,
    isInRemoveAreaProvider: () -> Boolean
): DragDropState {
    val state = remember(lazyListState) {
        DragDropState(
            context = context,
            draggableItemsNum = draggableItemsNum,
            stateList = lazyListState,
            onMove = onMove,
            removeIconVisible = removeIconVisible,
            onRemove = onRemove,
            isInRemoveAreaProvider = isInRemoveAreaProvider
        )
    }
    LaunchedEffect(state) {
        while (true) {
            val diff = state.scrollChannel.receive()
            lazyListState.scrollBy(diff)
        }
    }
    return state
}

class DragDropState(
    private val context: Context,
    private val draggableItemsNum: Int,
    private val stateList: LazyListState,
    private val onMove: (Int, Int) -> Unit,
    private val removeIconVisible: (Boolean) -> Unit,
    private val onRemove: (Int) -> Unit,
    private val isInRemoveAreaProvider: () -> Boolean
) {
    var draggingItemIndex: Int? by mutableStateOf(null)
    var deltaX by mutableFloatStateOf(0f)
    var deltaY by mutableFloatStateOf(0f)
    val scrollChannel = Channel<Float>()

    private var draggingItem: LazyListItemInfo? = null

    internal fun onDragStart(offset: Offset) {
        stateList.layoutInfo.visibleItemsInfo
            .firstOrNull { item -> offset.y.toInt() in item.offset..(item.offset + item.size) }
            ?.also {
                val index = it.index
                draggingItem = it
                draggingItemIndex = index
                removeIconVisible(true)
                context.vibrate(50)
            }
    }

    internal fun onDragInterrupted() {
        if (isInRemoveAreaProvider()) {
            draggingItemIndex?.let { index ->
                onRemove(index)
            }
        }
        removeIconVisible(false)
        draggingItem = null
        draggingItemIndex = null
        deltaX = 0f
        deltaY = 0f
    }

    internal fun onDrag(offset: Offset) {
        deltaX += offset.x
        deltaY += offset.y
        val currentDraggingItemIndex = draggingItemIndex ?: return
        val currentDraggingItem = draggingItem ?: return

        val startOffset = currentDraggingItem.offset + deltaY
        val endOffset = currentDraggingItem.offset + currentDraggingItem.size + deltaY
        val middleOffset = startOffset + (endOffset - startOffset) / 2

        val targetItem = stateList.layoutInfo.visibleItemsInfo.find { item ->
            middleOffset.toInt() in item.offset..item.offset + item.size &&
                currentDraggingItem.index != item.index
        }

        if (targetItem != null) {
            val targetIndex = targetItem.index
            onMove(currentDraggingItemIndex, targetIndex)
            draggingItemIndex = targetIndex
            deltaY += currentDraggingItem.offset - targetItem.offset
            draggingItem = targetItem
        } else {
            val startOffsetToTop = startOffset - stateList.layoutInfo.viewportStartOffset
            val endOffsetToBottom = endOffset - stateList.layoutInfo.viewportEndOffset
            val scroll = when {
                startOffsetToTop < 0 -> startOffsetToTop.coerceAtMost(0f)
                endOffsetToBottom > 0 -> endOffsetToBottom.coerceAtLeast(0f)
                else -> 0f
            }

            if (scroll != 0f && currentDraggingItemIndex != 0 && currentDraggingItemIndex != draggableItemsNum - 1) {
                scrollChannel.trySend(scroll)
            }
        }
    }
}
