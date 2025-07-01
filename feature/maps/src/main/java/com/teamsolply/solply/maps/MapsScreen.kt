package com.teamsolply.solply.maps

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.naver.maps.map.compose.ExperimentalNaverMapApi
import com.naver.maps.map.compose.NaverMap
import com.teamsolply.solply.maps.bottomsheet.course.CourseBottomSheet
import com.teamsolply.solply.maps.component.MapsTopBar
import com.teamsolply.solply.maps.model.CourseInfo
import com.teamsolply.solply.ui.extension.vibrate
import com.teamsolply.solply.ui.lifecycle.LaunchedEffectWithLifecycle
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch

@Composable
fun MapsRoute(
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
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalNaverMapApi::class)
@Composable
fun MapsScreen(
    context: Context,
    course: List<CourseInfo>,
    removeIconVisible: Boolean,
    startCourseMove: (Boolean) -> Unit,
    moveCourse: (fromIndex: Int, toIndex: Int) -> Unit,
    removeCourse: (itemToRemove: Int) -> Unit,
    modifier: Modifier = Modifier
) {
    val scope = rememberCoroutineScope()
    var scrollToIndex by remember { mutableStateOf<Int?>(null) }
    val lazyListState = rememberLazyListState()

    val isInRemoveIconArea = remember { mutableStateOf(false) }
    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true
    )

    LaunchedEffect(scrollToIndex) {
        scrollToIndex?.let { index ->
            lazyListState.animateScrollToItem(index)
            scrollToIndex = null
        }
    }

    LaunchedEffect(Unit) {
        sheetState.show()
        snapshotFlow { isInRemoveIconArea }
            .distinctUntilChanged()
            .collect { isInArea ->
                if (isInArea.value) {
                    context.vibrate(75)
                }
            }
    }
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        MapsTopBar(
            title = "코스 상세보기",
            onBackClick = {},
            onGoToHome = {}
        )
        NaverMap(
            modifier = Modifier.fillMaxSize(),
        )
    }

    CourseBottomSheet(
        course = course,
        context = context,
        removeIconVisible = removeIconVisible,
        isInRemoveIconArea = isInRemoveIconArea,
        lazyListState = lazyListState,
        startCourseMove = startCourseMove,
        moveCourse = moveCourse,
        removeCourse = removeCourse,
        onDismissRequest = {
            scope.launch {
                sheetState.hide()
            }
        },
        sheetState = sheetState
    )
}
