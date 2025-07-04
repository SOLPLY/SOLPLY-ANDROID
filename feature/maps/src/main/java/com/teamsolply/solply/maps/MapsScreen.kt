package com.teamsolply.solply.maps

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
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
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.CameraPosition
import com.naver.maps.map.compose.ExperimentalNaverMapApi
import com.naver.maps.map.compose.Marker
import com.naver.maps.map.compose.MarkerState
import com.naver.maps.map.compose.NaverMap
import com.naver.maps.map.compose.PathOverlay
import com.naver.maps.map.compose.rememberCameraPositionState
import com.naver.maps.map.overlay.OverlayImage
import com.teamsolply.solply.designsystem.component.bottomsheet.SolplyBasicBottomSheet
import com.teamsolply.solply.designsystem.component.button.AddCourseButton
import com.teamsolply.solply.designsystem.component.button.AddPlaceButton
import com.teamsolply.solply.designsystem.theme.SolplyTheme
import com.teamsolply.solply.maps.addcourse.AddCourseBottomSheet
import com.teamsolply.solply.maps.component.MapsTopBar
import com.teamsolply.solply.maps.editcourse.EditCourseBottomSheet
import com.teamsolply.solply.maps.editcourse.interaction.rememberDragDropState
import com.teamsolply.solply.maps.model.CourseInfo
import com.teamsolply.solply.maps.placedetail.PlaceDetailBottomSheet
import com.teamsolply.solply.maps.util.navigateToNaverMapDirections
import com.teamsolply.solply.model.MapsType
import com.teamsolply.solply.ui.extension.customClickable
import com.teamsolply.solply.ui.extension.vibrate
import com.teamsolply.solply.ui.lifecycle.LaunchedEffectWithLifecycle
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged

@Composable
fun MapsRoute(
    mapsType: MapsType,
    showDisabledRemoveCourseSnackBar: (String) -> Unit,
    navigatePlaceDetail: () -> Unit,
    navigateToPlace: () -> Unit,
    navigateToCourse: () -> Unit,
    navigateToMypage: () -> Unit,
    navigateToBack: () -> Unit,
    paddingValues: PaddingValues,
    viewModel: MapsViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffectWithLifecycle {
        viewModel.sideEffect.collectLatest { sideEffect ->
            when (sideEffect) {
                MapsSideEffect.DisabledRemoveCourse -> {
                    showDisabledRemoveCourseSnackBar("코스 안에 2개 이상의 장소가 남아있어야 해요.")
                }

                MapsSideEffect.NavigateToReturnHome -> when (mapsType) {
                    MapsType.PLACE_DETAIL -> navigateToPlace()
                    MapsType.ADD_COURSE -> navigateToCourse()
                    MapsType.EDIT_COURSE -> navigateToMypage()
                }

                MapsSideEffect.NavigateToBack -> navigateToBack()
            }
        }
    }

    MapsScreen(
        mapsType = mapsType,
        context = context,
        courses = uiState.courses,
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
        onReturnToHomeClick = {
            viewModel.sendIntent(MapsIntent.ReturnToHomeClick)
        },
        onBackButtonClick = {
            viewModel.sendIntent(MapsIntent.BackButtonClick)
        }
    )
}

@OptIn(ExperimentalNaverMapApi::class)
@Composable
fun MapsScreen(
    mapsType: MapsType,
    context: Context,
    courses: List<CourseInfo>,
    removeIconVisible: Boolean,
    startCourseMove: (Boolean) -> Unit,
    moveCourse: (fromIndex: Int, toIndex: Int) -> Unit,
    removeCourse: (itemToRemove: Int) -> Unit,
    onReturnToHomeClick: () -> Unit,
    onBackButtonClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val scope = rememberCoroutineScope()
    var scrollToIndex by remember { mutableStateOf<Int?>(null) }
    val lazyListState = rememberLazyListState()

    val isInRemoveIconArea = remember { mutableStateOf(false) }
    var removeIconBounds by remember { mutableStateOf<Rect?>(null) }
    val draggableItemSize by remember { derivedStateOf { courses.size } }
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

    val cameraPositionState = rememberCameraPositionState {
        position = if (courses.isNotEmpty()) {
            val firstCourse = courses.first()
            CameraPosition(
                LatLng(firstCourse.latitude - 0.004, firstCourse.longitude),
                14.0,
                0.0,
                0.0
            )
        } else CameraPosition(
            LatLng(37.5665, 126.9780),
            14.0,
            0.0,
            0.0
        )
    }

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
                // TODO("장소 상세 이름으로")
            }
            MapsTopBar(
                mapsType = mapsType,
                title = topBarTitle,
                onBackButtonClick = { onBackButtonClick() },
                onReturnToHomeButtonClick = { onReturnToHomeClick() }
            )
            NaverMap(
                modifier = Modifier.fillMaxSize(),
                cameraPositionState = cameraPositionState
            ) {
                courses.forEachIndexed { index, course ->
                    val markerIconRes = when (index) {
                        0 -> com.teamsolply.solply.designsystem.R.drawable.ic_marker_first
                        1 -> com.teamsolply.solply.designsystem.R.drawable.ic_marker_second
                        2 -> com.teamsolply.solply.designsystem.R.drawable.ic_marker_third
                        3 -> com.teamsolply.solply.designsystem.R.drawable.ic_marker_fourth
                        4 -> com.teamsolply.solply.designsystem.R.drawable.ic_marker_fifth
                        5 -> com.teamsolply.solply.designsystem.R.drawable.ic_marker_sixth
                        else -> com.teamsolply.solply.designsystem.R.drawable.ic_marker_default
                    }
                    val currentLatLng = LatLng(course.latitude, course.longitude)
                    Marker(
                        state = MarkerState(position = LatLng(course.latitude, course.longitude)),
                        icon = OverlayImage.fromResource(markerIconRes),
                        anchor = Offset(0.5f, 0.5f)
                    )
                    if (index < courses.lastIndex) {
                        val nextCourse = courses[index + 1]
                        val nextLatLng = LatLng(nextCourse.latitude, nextCourse.longitude)

                        PathOverlay(
                            coords = listOf(currentLatLng, nextLatLng),
                            color = SolplyTheme.colors.purple900,
                            width = 0.5.dp
                        )
                    }
                }
            }
        }

        SolplyBasicBottomSheet(
            modifier = Modifier.align(Alignment.BottomCenter),
            menuContent = {
                if (mapsType == MapsType.PLACE_DETAIL) {
                    Box(
                        modifier = Modifier
                            .size(47.dp)
                            .background(color = SolplyTheme.colors.white, shape = CircleShape)
                            .customClickable(rippleEnabled = false) {
                                navigateToNaverMapDirections(
                                    context = context,
                                    destName = "강남역",
                                    destId = "222",
                                    destLongitude = 127.02760,
                                    destLatitude = 37.49794,
                                    destType = "SUBWAY_STATION"
                                )
                            },
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            painter = painterResource(com.teamsolply.solply.designsystem.R.drawable.ic_place_navigation),
                            contentDescription = "place_navigation",
                            tint = Color.Unspecified
                        )
                    }
                    Spacer(modifier = Modifier.weight(1f))
                    AddPlaceButton(
                        onClick = {},
                        isAddPlace = false,
                        selected = true,
                        modifier = Modifier.padding(end = 8.dp)
                    )

                    AddPlaceButton(
                        onClick = {},
                        isAddPlace = true,
                        selected = true
                    )
                } else {
                    AddCourseButton(
                        onClick = {},
                        selected = true
                    )
                }
                // TODO("맵 타입에 따라 바텀 시트 위 버튼")
            },
            content = {
                when (mapsType) {
                    MapsType.PLACE_DETAIL -> {
                        PlaceDetailBottomSheet()
                    }

                    MapsType.ADD_COURSE -> {
                        AddCourseBottomSheet()
                    }

                    MapsType.EDIT_COURSE -> {
                        EditCourseBottomSheet(
                            course = courses,
                            removeIconBounds = removeIconBounds,
                            isInRemoveIconArea = isInRemoveIconArea,
                            rootCoordinatesState = rootCoordinatesState,
                            touchPositionState = touchPositionState,
                            lazyListState = lazyListState,
                            dragDropState = dragDropState
                        )
                    }
                }
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
