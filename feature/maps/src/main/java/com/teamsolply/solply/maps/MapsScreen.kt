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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.shadow
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
import com.naver.maps.map.CameraUpdate
import com.naver.maps.map.compose.ExperimentalNaverMapApi
import com.naver.maps.map.compose.Marker
import com.naver.maps.map.compose.MarkerState
import com.naver.maps.map.compose.NaverMap
import com.naver.maps.map.compose.PathOverlay
import com.naver.maps.map.compose.rememberCameraPositionState
import com.naver.maps.map.overlay.OverlayImage
import com.teamsolply.solply.designsystem.R
import com.teamsolply.solply.designsystem.component.bottomsheet.SolplyBasicBottomSheet
import com.teamsolply.solply.designsystem.component.button.AddCourseButton
import com.teamsolply.solply.designsystem.component.button.AddPlaceButton
import com.teamsolply.solply.designsystem.component.button.SolplyBasicButton
import com.teamsolply.solply.designsystem.component.dialog.SolplyConfirmDialog
import com.teamsolply.solply.designsystem.theme.SolplyTheme
import com.teamsolply.solply.maps.component.MapsTopBar
import com.teamsolply.solply.maps.component.bottomsheet.AddCourseBottomSheet
import com.teamsolply.solply.maps.component.bottomsheet.EditCourseBottomSheet
import com.teamsolply.solply.maps.component.bottomsheet.PlaceDetailBottomSheet
import com.teamsolply.solply.maps.component.dialog.CourseSaveDialog
import com.teamsolply.solply.maps.model.CourseDetailEntity
import com.teamsolply.solply.maps.model.CourseInfoEntity
import com.teamsolply.solply.maps.model.CourseSaveType
import com.teamsolply.solply.maps.model.Place
import com.teamsolply.solply.maps.model.PlaceDetailEntity
import com.teamsolply.solply.maps.util.calculateCameraPosition
import com.teamsolply.solply.maps.util.navigateToNaverMapDirections
import com.teamsolply.solply.model.MapsType
import com.teamsolply.solply.ui.extension.customClickable
import com.teamsolply.solply.ui.extension.vibrate
import com.teamsolply.solply.ui.lifecycle.LaunchedEffectWithLifecycle
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlin.math.abs

@Composable
internal fun MapsRoute(
    mapsType: MapsType,
    targetId: Int = 1,
    showTextSnackBar: (String) -> Unit,
    showNotificationSnackBar: (String) -> Unit,
    showNavigateSnackBar: (String, () -> Unit) -> Unit,
    navigatePlaceDetail: () -> Unit,
    navigateToEditCourse: () -> Unit,
    navigateToPlace: () -> Unit,
    navigateToCourse: () -> Unit,
    navigateToMypage: () -> Unit,
    navigateToBack: () -> Unit,
    paddingValues: PaddingValues,
    viewModel: MapsViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    // TODO. 초기 로드 데이터
    LaunchedEffect(Unit) {
        when (mapsType) {
            MapsType.PLACE_DETAIL -> {
                viewModel.getPlaceDetailInfo(targetId)
                viewModel.getAllCourseInfo()
            }

            MapsType.ADD_COURSE -> {
                viewModel.getCourseDetailInfo()
            }

            MapsType.EDIT_COURSE -> {
                viewModel.getCourseDetailInfo()
            }
        }
    }

    LaunchedEffectWithLifecycle {
        viewModel.sideEffect.collectLatest { sideEffect ->
            when (sideEffect) {
                MapsSideEffect.DisabledRemoveCourse -> {
                    showNotificationSnackBar("코스 안에 2개 이상의 장소가 남아있어야 해요.")
                }

                MapsSideEffect.ShowMaxSizeCourseSnackBar -> showNotificationSnackBar(
                    "코스에 이미 6개의 장소가 꽉 차 있어요"
                )

                is MapsSideEffect.ShowSuccessSaveCourseSnackBar -> {
                    showNavigateSnackBar(
                        sideEffect.selectedCourseName
                    ) {
                        navigateToEditCourse()
                    }
                }

                is MapsSideEffect.ShowSuccessSavePlaceSnackBar -> {
                    showTextSnackBar("장소가 수집함에 저장되었어요.")
                }

                is MapsSideEffect.ShowSuccessSaveSingleCourseSnackBar -> {
                    showNavigateSnackBar("코스가 수집함에 저장되었어요.") {
                        navigateToMypage()
                    }
                }

                is MapsSideEffect.ShowSuccessSaveNewCourseSnackBar -> {
                    showTextSnackBar("새 코스로 저장되었어요.")
                }

                MapsSideEffect.NavigateToCourse -> {
                    navigateToCourse()
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
        placeDetailEntity = uiState.placeDetailInfo,
        startAddMyCourse = uiState.startAddMyCourse,
        courses = uiState.courses,
        addMyCourseSelectedCount = uiState.addMyCourseSelectedCount,
        placeBookmarked = uiState.placeDetailInfo.isBookmarked,
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
            viewModel.sendIntent(MapsIntent.SavePlaceInMyCourse)
        },
        placeBookMarkClick = {
            viewModel.sendIntent(MapsIntent.PlaceBookMarkClick)
        },
        // ADD Course
        courseDetailInfo = uiState.courseDetailInfo,
        saveCourse = { viewModel.sendIntent(MapsIntent.SaveCourse) },
        selectedPlaceInfoId = uiState.selectedPlaceInfoId,
        singleCoursePlaceBookMarkClick = { placeId ->
            viewModel.sendIntent(MapsIntent.SingleCoursePlaceBookMarkClick(placeId))
        },
        placeInfoClick = { placeId ->
            viewModel.sendIntent(MapsIntent.PlaceInfoClick(placeId = placeId))
        },
        // Edit Course
        removeIconVisible = uiState.removeIconVisibility,
        startEditCourse = uiState.startEditCourse,
        coursesBeforeEdit = uiState.coursesBeforeEdit,
        startCourseMove = { iconVisibility ->
            viewModel.sendIntent(MapsIntent.StartCourseMove(iconVisibility = iconVisibility))
        },
        moveCourse = { from, to ->
            viewModel.sendIntent(MapsIntent.MoveCourseItem(fromIndex = from, toIndex = to))
        },
        removeCourse = { remove ->
            viewModel.sendIntent(MapsIntent.RemoveCourseItem(itemToRemove = remove))
        },
        emptyCourseClick = {
            viewModel.sendIntent(MapsIntent.EmptyCourseClick)
        },
        onReturnToHomeClick = {
            viewModel.sendIntent(MapsIntent.ReturnToHomeClick)
        },
        onBackButtonClick = {
            viewModel.sendIntent(MapsIntent.BackButtonClick)
        },
        onStartEditCourseClick = {
            viewModel.sendIntent(MapsIntent.StartEditCourseIconClick)
        },
        onCourseEditBackClick = {
            viewModel.sendIntent(MapsIntent.BeforeEditCourseBackHandler)
        }
    )

    if (uiState.courseSaveDialogVisibility) {
        CourseSaveDialog(
            saveToCourseClick = {
                viewModel.sendIntent(
                    MapsIntent.CourseSaveDialogClick(
                        CourseSaveType.SaveToExistingCourse
                    )
                )
            },
            saveToNewCourseClick = {
                viewModel.sendIntent(
                    MapsIntent.CourseSaveDialogClick(
                        CourseSaveType.SaveAsNewCourse
                    )
                )
            },
            onDismissRequest = { viewModel.sendIntent(MapsIntent.ChangeCourseSaveDialogInVisibility) }
        )
    }

    if (uiState.exitEditCourseDialogVisibility) {
        SolplyConfirmDialog(
            text = "변경 사항을 저장하지 않고\n나가시겠어요?",
            confirmButtonText = "나가기",
            dismissButtonText = "취소",
            onClickConfirm = { viewModel.sendIntent(MapsIntent.BeforeEditCourseDialogClick) },
            onClickDismiss = { viewModel.sendIntent(MapsIntent.BeforeEditCourseDialogInVisible) }
        )
    }
}

@OptIn(ExperimentalNaverMapApi::class)
@Composable
private fun MapsScreen(
    mapsType: MapsType,
    context: Context,
    // Add Place
    placeDetailEntity: PlaceDetailEntity,
    startAddMyCourse: Boolean,
    courses: PersistentList<CourseInfoEntity>,
    addMyCourseSelectedCount: PersistentList<Int>,
    placeBookmarked: Boolean,
    changeAddPlaceState: (Boolean) -> Unit,
    selectedCourseForPlace: (Int) -> Unit,
    showMaxSizeCourseSnackBar: () -> Unit,
    saveMyCourse: () -> Unit,
    placeBookMarkClick: () -> Unit,
    // Add Course
    courseDetailInfo: CourseDetailEntity,
    saveCourse: () -> Unit,
    selectedPlaceInfoId: Int?,
    singleCoursePlaceBookMarkClick: (Int) -> Unit,
    placeInfoClick: (Int) -> Unit,
    // Edit Course
    removeIconVisible: Boolean,
    startEditCourse: Boolean,
    coursesBeforeEdit: ImmutableList<Place>,
    startCourseMove: (Boolean) -> Unit,
    moveCourse: (fromIndex: Int, toIndex: Int) -> Unit,
    removeCourse: (itemToRemove: Int) -> Unit,
    emptyCourseClick: () -> Unit,
    onReturnToHomeClick: () -> Unit,
    onBackButtonClick: () -> Unit,
    onStartEditCourseClick: () -> Unit,
    onCourseEditBackClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val isInRemoveIconArea = remember { mutableStateOf(false) }
    var removeIconBounds by remember { mutableStateOf<Rect?>(null) }
    val rootCoordinatesState = remember { mutableStateOf<LayoutCoordinates?>(null) }
    val touchPositionState = remember { mutableStateOf(Offset.Zero) }

    val cameraPositionState = rememberCameraPositionState()
    var lastCameraPosition by remember { mutableStateOf<CameraPosition?>(null) }

    LaunchedEffect(mapsType, placeDetailEntity, courseDetailInfo.places) {
        when (mapsType) {
            MapsType.PLACE_DETAIL -> {
                cameraPositionState.animate(
                    update = CameraUpdate.toCameraPosition(
                        CameraPosition(
                            LatLng(placeDetailEntity.latitude - 0.008, placeDetailEntity.longitude),
                            14.0,
                            0.0,
                            0.0
                        )
                    ),
                    durationMs = 1000
                )
            }

            MapsType.ADD_COURSE, MapsType.EDIT_COURSE -> {
                if (courseDetailInfo.places.isNotEmpty()) {
                    val newCameraPosition = calculateCameraPosition(courseDetailInfo.places)

                    val shouldAnimate = lastCameraPosition?.let { lastPos ->
                        val latDiff =
                            abs(newCameraPosition.target.latitude - lastPos.target.latitude)
                        val lngDiff =
                            abs(newCameraPosition.target.longitude - lastPos.target.longitude)
                        latDiff > 0.01 || lngDiff > 0.01
                    } ?: true

                    if (shouldAnimate) {
                        cameraPositionState.animate(
                            update = CameraUpdate.toCameraPosition(newCameraPosition),
                            durationMs = 1000
                        )
                        lastCameraPosition = newCameraPosition
                    }
                }
            }
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
                else -> placeDetailEntity.placeName
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
                                placeDetailEntity.latitude,
                                placeDetailEntity.longitude
                            )
                        ),
                        icon = OverlayImage.fromResource(R.drawable.ic_marker_default),
                        anchor = Offset(0.5f, 0.5f)
                    )
                } else {
                    if (courseDetailInfo.places.isNotEmpty()) {
                        courseDetailInfo.places.forEachIndexed { index, place ->
                            val markerIconRes = getMarkerIcon(
                                index = index,
                                isSelected = selectedPlaceInfoId == place.placeId
                            )
                            val currentLatLng =
                                LatLng(place.latitude.toDouble(), place.longitude.toDouble())
                            Marker(
                                state = MarkerState(
                                    position = LatLng(
                                        place.latitude.toDouble(),
                                        place.longitude.toDouble()
                                    )
                                ),
                                icon = OverlayImage.fromResource(markerIconRes),
                                anchor = Offset(0.5f, 0.5f)
                            )
                            if (index < courseDetailInfo.places.lastIndex) {
                                val nextCourse = courseDetailInfo.places[index + 1]
                                val nextLatLng = LatLng(
                                    nextCourse.latitude.toDouble(),
                                    nextCourse.longitude.toDouble()
                                )

                                PathOverlay(
                                    coords = persistentListOf(currentLatLng, nextLatLng),
                                    color = SolplyTheme.colors.purple900,
                                    width = 0.5.dp
                                )
                            }
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
                            .shadow(
                                elevation = 8.dp,
                                shape = CircleShape,
                                clip = false
                            )
                            .background(color = SolplyTheme.colors.white, shape = CircleShape)
                            .then(
                                if (startAddMyCourse) {
                                    Modifier
                                } else {
                                    Modifier.customClickable(rippleEnabled = false) {
                                        navigateToNaverMapDirections(
                                            context = context,
                                            destName = placeDetailEntity.placeName,
                                            destId = placeDetailEntity.placeDefaultId.toString(),
                                            destLongitude = placeDetailEntity.longitude,
                                            destLatitude = placeDetailEntity.latitude,
                                            destType = placeDetailEntity.placeType
                                        )
                                    }
                                }
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.ic_place_navigation),
                            contentDescription = "place_navigation",
                            tint = if (startAddMyCourse) SolplyTheme.colors.gray400 else Color.Unspecified
                        )
                    }
                    Spacer(modifier = Modifier.weight(1f))
                    AddPlaceButton(
                        onClick = { changeAddPlaceState(!startAddMyCourse) },
                        selected = startAddMyCourse,
                        modifier = Modifier
                            .padding(end = 8.dp)
                            .shadow(
                                elevation = 8.dp,
                                shape = CircleShape,
                                clip = false
                            )
                    )

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center,
                        modifier = Modifier
                            .height(49.dp)
                            .padding(end = 16.dp)
                            .shadow(
                                elevation = 8.dp,
                                shape = CircleShape,
                                clip = false
                            )
                            .background(
                                color = if (startAddMyCourse) {
                                    SolplyTheme.colors.white
                                } else {
                                    if (placeBookmarked) {
                                        SolplyTheme.colors.red100
                                    } else {
                                        SolplyTheme.colors.white
                                    }
                                },
                                shape = RoundedCornerShape(26.dp)
                            )
                            .then(
                                if (startAddMyCourse) {
                                    Modifier
                                } else {
                                    Modifier.customClickable(rippleEnabled = false) {
                                        placeBookMarkClick()
                                    }
                                }
                            )
                    ) {
                        Text(
                            text = "장소 저장",
                            modifier = Modifier.padding(start = 16.dp),
                            style = SolplyTheme.typography.body14M,
                            color = if (startAddMyCourse) {
                                SolplyTheme.colors.gray400
                            } else {
                                if (placeBookmarked) {
                                    SolplyTheme.colors.red500
                                } else {
                                    SolplyTheme.colors.purple600
                                }
                            },
                            maxLines = 1
                        )
                        Icon(
                            painter = painterResource(R.drawable.ic_marker_default),
                            contentDescription = "add_place",
                            modifier = Modifier.padding(start = 8.dp, end = 15.dp),
                            tint = if (startAddMyCourse) {
                                SolplyTheme.colors.gray400
                            } else {
                                if (placeBookmarked) SolplyTheme.colors.red500 else SolplyTheme.colors.purple600
                            }
                        )
                    }
                } else {
                    AddCourseButton(
                        onClick = saveCourse,
                        selected = courseDetailInfo.isBookmarked,
                        modifier = Modifier
                            .padding(end = 15.dp)
                            .shadow(
                                elevation = 8.dp,
                                shape = CircleShape,
                                clip = false
                            )
                    )
                }
            },
            content = {
                when (mapsType) {
                    MapsType.PLACE_DETAIL -> {
                        PlaceDetailBottomSheet(
                            addPlace = startAddMyCourse,
                            placeType = placeDetailEntity.primaryTag,
                            title = placeDetailEntity.placeName,
                            description = placeDetailEntity.description,
                            placeImageUrls = placeDetailEntity.imageInfos.toPersistentList(),
                            placeAddress = placeDetailEntity.address,
                            placeContactNumber = placeDetailEntity.contactNumber,
                            placeOpeningHours = placeDetailEntity.openingHours,
                            placeSnsLink = placeDetailEntity.snsLink.toPersistentList(),
                            courses = courses,
                            addMyCourseSelectedCount = addMyCourseSelectedCount,
                            addMyCourseBackClick = { changeAddPlaceState(!startAddMyCourse) },
                            selectedCourseForPlace = selectedCourseForPlace,
                            showMaxSizeCourseSnackBar = showMaxSizeCourseSnackBar,
                            emptyCourseClick = emptyCourseClick
                        )
                    }

                    MapsType.ADD_COURSE -> {
                        AddCourseBottomSheet(
                            places = courseDetailInfo.places.toPersistentList(),
                            courseName = courseDetailInfo.courseName,
                            introduction = courseDetailInfo.introduction,
                            selectedPlaceItem = selectedPlaceInfoId,
                            singleCoursePlaceBookMarkClick = singleCoursePlaceBookMarkClick,
                            placeInfoClick = placeInfoClick
                        )
                    }

                    MapsType.EDIT_COURSE -> {
                        EditCourseBottomSheet(
                            context = context,
                            places = courseDetailInfo.places.toPersistentList(),
                            courseName = courseDetailInfo.courseName,
                            introduction = courseDetailInfo.introduction,
                            selectedPlaceItem = selectedPlaceInfoId,
                            removeIconBounds = removeIconBounds,
                            isInRemoveIconArea = isInRemoveIconArea,
                            rootCoordinatesState = rootCoordinatesState,
                            touchPositionState = touchPositionState,
                            startEditCourse = startEditCourse,
                            coursesBeforeEdit = coursesBeforeEdit,
                            singleCoursePlaceBookMarkClick = singleCoursePlaceBookMarkClick,
                            onStartEditCourseClick = onStartEditCourseClick,
                            placeInfoClick = placeInfoClick,
                            startCourseMove = startCourseMove,
                            moveCourse = moveCourse,
                            removeCourse = removeCourse,
                            onCourseEditBackClick = onCourseEditBackClick
                        )
                    }
                }
            }
        )

        Icon(
            painter = painterResource(
                if (isInRemoveIconArea.value) {
                    R.drawable.ic_remove_floating_on
                } else {
                    R.drawable.ic_remove_floating
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

@Composable
private fun getMarkerIcon(index: Int, isSelected: Boolean): Int {
    return remember(index, isSelected) {
        when (index) {
            0 -> if (isSelected) R.drawable.ic_marker_selected_first else R.drawable.ic_marker_first
            1 -> if (isSelected) R.drawable.ic_marker_selected_second else R.drawable.ic_marker_second
            2 -> if (isSelected) R.drawable.ic_marker_selected_third else R.drawable.ic_marker_third
            3 -> if (isSelected) R.drawable.ic_marker_selected_fourth else R.drawable.ic_marker_fourth
            4 -> if (isSelected) R.drawable.ic_marker_selected_fifth else R.drawable.ic_marker_fifth
            5 -> if (isSelected) R.drawable.ic_marker_selected_sixth else R.drawable.ic_marker_sixth
            else -> R.drawable.ic_marker_default
        }
    }
}
