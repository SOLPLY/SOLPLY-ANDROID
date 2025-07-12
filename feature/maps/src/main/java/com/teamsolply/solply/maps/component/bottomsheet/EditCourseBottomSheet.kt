package com.teamsolply.solply.maps.component.bottomsheet

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.LayoutCoordinates
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.teamsolply.solply.designsystem.R
import com.teamsolply.solply.designsystem.theme.SolplyTheme
import com.teamsolply.solply.maps.component.CourseItem
import com.teamsolply.solply.maps.courseDetailEntity
import com.teamsolply.solply.maps.util.dragContainer
import com.teamsolply.solply.maps.util.draggableItems
import com.teamsolply.solply.maps.util.rememberDragDropState
import com.teamsolply.solply.maps.model.Place
import com.teamsolply.solply.model.PlaceType
import com.teamsolply.solply.ui.extension.customClickable
import kotlinx.collections.immutable.PersistentList

@Composable
internal fun EditCourseBottomSheet(
    context: Context,
    places: PersistentList<Place>,
    courseName: String,
    introduction: String,
    selectedPlaceItem: Int?,
    removeIconBounds: Rect?,
    isInRemoveIconArea: MutableState<Boolean>,
    rootCoordinatesState: MutableState<LayoutCoordinates?>,
    touchPositionState: MutableState<Offset>,
    startEditCourse: Boolean,
    singleCoursePlaceBookMarkClick: (Int) -> Unit,
    onStartEditCourseClick: () -> Unit,
    placeInfoClick: (Int) -> Unit,
    startCourseMove: (Boolean) -> Unit,
    moveCourse: (fromIndex: Int, toIndex: Int) -> Unit,
    removeCourse: (itemToRemove: Int) -> Unit,
) {
    val draggableItemSize by remember(courseDetailEntity.places.size) {
        derivedStateOf { courseDetailEntity.places.size }
    }
    var scrollToIndex by remember { mutableStateOf<Int?>(null) }

    val lazyListState = rememberLazyListState()
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
        Column(
            modifier = Modifier.padding(horizontal = 20.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.Start
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = courseName,
                    modifier = Modifier.padding(bottom = 4.dp),
                    color = SolplyTheme.colors.black,
                    style = SolplyTheme.typography.title18Sb
                )
                Box(
                    modifier = Modifier.customClickable(rippleEnabled = false) {
                        onStartEditCourseClick()
                    }
                ) {
                    if (startEditCourse) {
                        Icon(
                            painter = painterResource(R.drawable.ic_small_check),
                            contentDescription = "start_course_edit",
                            tint = SolplyTheme.colors.black
                        )
                    } else {
                        Icon(
                            painter = painterResource(R.drawable.ic_course_edit),
                            contentDescription = "start_course_edit",
                            tint = Color.Unspecified
                        )
                    }
                }
            }
            Text(
                text = introduction,
                modifier = Modifier.padding(bottom = 20.dp),
                color = SolplyTheme.colors.gray900,
                style = SolplyTheme.typography.caption14R
            )

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .then(
                        if (startEditCourse) Modifier.dragContainer(dragDropState) else Modifier
                    ),
                state = lazyListState,
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                draggableItems(
                    items = places,
                    dragDropState = dragDropState,
                    key = { _, item -> item.placeId }
                ) { index, modifier, item ->
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = modifier
                    ) {
                        if (!startEditCourse) {
                            Box(
                                modifier = Modifier
                                    .size(20.dp)
                                    .background(
                                        color = SolplyTheme.colors.gray200,
                                        shape = RoundedCornerShape(8.dp)
                                    ),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = (index + 1).toString(),
                                    color = SolplyTheme.colors.gray800,
                                    style = SolplyTheme.typography.caption12M
                                )
                            }
                            Spacer(modifier = Modifier.width(14.dp))
                        }
                        CourseItem(
                            placeName = item.placeName,
                            placeTag = PlaceType.entries.firstOrNull {
                                it.displayName == item.primaryTag
                            } ?: PlaceType.EMPTY,
                            placeAddress = item.address,
                            // TODO. 서버 이미지 url로 변경하기
                            // placeImageRes = item.thumbnailUrl,
                            placeImageRes = R.drawable.img_course_dummy,
                            modifier = Modifier.then(
                                if (startEditCourse) {
                                    Modifier
                                } else {
                                    Modifier.customClickable(
                                        rippleEnabled = false
                                    ) {
                                        placeInfoClick(item.placeId)
                                    }
                                }
                            ),
                            iconSelected = item.isBookmarked,
                            iconClick = { singleCoursePlaceBookMarkClick(item.placeId) },
                            selectedPlaceItem = selectedPlaceItem == item.placeId,
                            isEditing = startEditCourse
                        )
                    }
                }
            }
        }
    }
}
