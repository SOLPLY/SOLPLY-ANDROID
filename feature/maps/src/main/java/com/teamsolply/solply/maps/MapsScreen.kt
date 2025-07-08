package com.teamsolply.solply.maps

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
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
import com.teamsolply.solply.designsystem.component.button.SolplyBasicButton
import com.teamsolply.solply.designsystem.theme.SolplyTheme
import com.teamsolply.solply.maps.addcourse.AddCourseBottomSheet
import com.teamsolply.solply.maps.component.MapsTopBar
import com.teamsolply.solply.maps.editcourse.EditCourseBottomSheet
import com.teamsolply.solply.maps.editcourse.interaction.rememberDragDropState
import com.teamsolply.solply.maps.model.CourseInfo
import com.teamsolply.solply.maps.model.PlaceInfo
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
    targetId: Int = 1,
    showDisabledRemoveCourseSnackBar: (String) -> Unit,
    showMaxSizeCourseSnackBar: (String) -> Unit,
    showSuccessSaveCourseSnackBar: (String, () -> Unit) -> Unit,
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

                MapsSideEffect.ShowMaxSizeCourseSnackBar -> showMaxSizeCourseSnackBar(
                    "코스에 이미 6개의 장소가 꽉 차 있어요"
                )

                is MapsSideEffect.ShowSuccessSaveCourseSnackBar -> {
                    showSuccessSaveCourseSnackBar(
                        sideEffect.selectedCourseName
                    ) {
                        navigateToMypage()
                    }
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
        // Add Place
        placeInfo = uiState.placeInfo,
        startAddMyCourse = uiState.startAddMyCourse,
        courses = uiState.courses,
        addMyCourseSelectedCount = uiState.addMyCourseSelectedCount,
        changeAddPlaceState = { addPlace ->
            viewModel.sendIntent(MapsIntent.AddPlaceClick(addPlace = addPlace))
        },
        selectedCourseForPlace = { courseId ->
            viewModel.sendIntent(MapsIntent.SelectedCourseForPlace(courseId = courseId))
        },
        showMaxSizeCourseSnackBar = {
            viewModel.sendIntent(MapsIntent.ShowMaxSizeCourseSnackBar)
        },
        saveMyCourse = {
            viewModel.sendIntent(MapsIntent.SaveMyCourse)
        },
        // Edit Course
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
    // Add Place
    placeInfo: PlaceInfo,
    startAddMyCourse: Boolean,
    courses: List<CourseInfo>,
    addMyCourseSelectedCount: List<Int>,
    changeAddPlaceState: (Boolean) -> Unit,
    selectedCourseForPlace: (Int) -> Unit,
    showMaxSizeCourseSnackBar: () -> Unit,
    saveMyCourse: () -> Unit,
    // Edit Course
    course: List<PlaceInfo>,
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

    val cameraPositionState = rememberCameraPositionState {
        position = if (mapsType == MapsType.PLACE_DETAIL) {
            CameraPosition(
                LatLng(placeInfo.latitude - 0.008, placeInfo.longitude),
                14.0,
                0.0,
                0.0
            )
        } else {
            if (course.isNotEmpty()) {
                val firstCourse = course.first()
                CameraPosition(
                    LatLng(firstCourse.latitude - 0.008, firstCourse.longitude),
                    14.0,
                    0.0,
                    0.0
                )
            } else {
                CameraPosition(
                    LatLng(37.5665, 126.9780),
                    14.0,
                    0.0,
                    0.0
                )
            }
        }
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
                if (mapsType == MapsType.PLACE_DETAIL) {
                    Marker(
                        state = MarkerState(
                            position = LatLng(
                                placeInfo.latitude,
                                placeInfo.longitude
                            )
                        ),
                        icon = OverlayImage.fromResource(com.teamsolply.solply.designsystem.R.drawable.ic_marker_default),
                        anchor = Offset(0.5f, 0.5f)
                    )
                } else {
                    course.forEachIndexed { index, courseItem ->
                        val markerIconRes = when (index) {
                            0 -> com.teamsolply.solply.designsystem.R.drawable.ic_marker_first
                            1 -> com.teamsolply.solply.designsystem.R.drawable.ic_marker_second
                            2 -> com.teamsolply.solply.designsystem.R.drawable.ic_marker_third
                            3 -> com.teamsolply.solply.designsystem.R.drawable.ic_marker_fourth
                            4 -> com.teamsolply.solply.designsystem.R.drawable.ic_marker_fifth
                            5 -> com.teamsolply.solply.designsystem.R.drawable.ic_marker_sixth
                            else -> com.teamsolply.solply.designsystem.R.drawable.ic_marker_default
                        }
                        val currentLatLng = LatLng(courseItem.latitude, courseItem.longitude)
                        Marker(
                            state = MarkerState(
                                position = LatLng(
                                    courseItem.latitude,
                                    courseItem.longitude
                                )
                            ),
                            icon = OverlayImage.fromResource(markerIconRes),
                            anchor = Offset(0.5f, 0.5f)
                        )
                        if (index < course.lastIndex) {
                            val nextCourse = course[index + 1]
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
        }

        SolplyBasicBottomSheet(
            modifier = Modifier
                .align(Alignment.BottomCenter),
            menuContent = {
                if (mapsType == MapsType.PLACE_DETAIL) {
                    Box(
                        modifier = Modifier
                            .padding(start = 16.dp)
                            .size(47.dp)
                            .background(color = SolplyTheme.colors.white, shape = CircleShape)
                            .then(
                                if (startAddMyCourse) {
                                    Modifier
                                } else {
                                    Modifier.customClickable(rippleEnabled = false) {
                                        navigateToNaverMapDirections(
                                            context = context,
                                            destName = "강남역",
                                            destId = "222",
                                            destLongitude = 127.02760,
                                            destLatitude = 37.49794,
                                            destType = "SUBWAY_STATION"
                                        )
                                    }
                                }
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            painter = painterResource(com.teamsolply.solply.designsystem.R.drawable.ic_place_navigation),
                            contentDescription = "place_navigation",
                            tint = if (startAddMyCourse) SolplyTheme.colors.gray400 else Color.Unspecified
                        )
                    }
                    Spacer(modifier = Modifier.weight(1f))
                    AddPlaceButton(
                        onClick = { changeAddPlaceState(!startAddMyCourse) },
                        selected = startAddMyCourse,
                        modifier = Modifier.padding(end = 8.dp)
                    )

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center,
                        modifier = Modifier
                            .height(49.dp)
                            .padding(end = 16.dp)
                            .background(
                                color = SolplyTheme.colors.white,
                                shape = RoundedCornerShape(26.dp)
                            )
                            .then(
                                if (startAddMyCourse) {
                                    Modifier
                                } else {
                                    Modifier.customClickable(rippleEnabled = false) {
                                        // TODO 장소 개별 저장
                                    }
                                }
                            )
                    ) {
                        Text(
                            text = "장소 저장",
                            modifier = Modifier.padding(start = 16.dp),
                            style = SolplyTheme.typography.body14M,
                            color = if (startAddMyCourse) SolplyTheme.colors.gray400 else SolplyTheme.colors.purple600,
                            maxLines = 1
                        )
                        Icon(
                            painter = painterResource(com.teamsolply.solply.designsystem.R.drawable.ic_marker_default),
                            contentDescription = "add_place",
                            modifier = Modifier.padding(start = 8.dp, end = 15.dp),
                            tint = if (startAddMyCourse) SolplyTheme.colors.gray400 else SolplyTheme.colors.purple600
                        )
                    }
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
                        PlaceDetailBottomSheet(
                            addPlace = startAddMyCourse,
                            placeType = placeInfo.primaryTag,
                            title = placeInfo.placeName,
                            description = placeInfo.description,
                            placeImageUrls = placeInfo.imageUrls,
                            placeAddress = placeInfo.address,
                            placeContactNumber = placeInfo.contactNumber,
                            placeOpeningHours = placeInfo.openingHours,
                            placeSnsLink = placeInfo.snsLink,
                            courses = courses,
                            addMyCourseSelectedCount = addMyCourseSelectedCount,
                            addMyCourseBackClick = { changeAddPlaceState(!startAddMyCourse) },
                            selectedCourseForPlace = selectedCourseForPlace,
                            showMaxSizeCourseSnackBar = showMaxSizeCourseSnackBar
                        )
                    }

                    MapsType.ADD_COURSE -> {
                        AddCourseBottomSheet()
                    }

                    MapsType.EDIT_COURSE -> {
                        EditCourseBottomSheet(
                            course = course,
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
            painter = painterResource(
                if (isInRemoveIconArea.value) {
                    com.teamsolply.solply.designsystem.R.drawable.ic_remove_floating_on
                } else {
                    com.teamsolply.solply.designsystem.R.drawable.ic_remove_floating
                }
            ),
            contentDescription = "remove",
            modifier = Modifier
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

        if (mapsType == MapsType.PLACE_DETAIL && addMyCourseSelectedCount.isNotEmpty()) {
            SolplyBasicButton(
                text = "이 코스에 추가할래요",
                onClick = {
                    saveMyCourse()
                    changeAddPlaceState(false)
                },
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(start = 20.dp, end = 20.dp, bottom = 24.dp),
                textPadding = PaddingValues(vertical = 21.dp),
                enabledBackgroundColor = SolplyTheme.colors.gray900
            )
        }
    }
}
