package com.teamsolply.solply.maps.component.dialog

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.minimumInteractiveComponentSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.teamsolply.solply.designsystem.R
import com.teamsolply.solply.designsystem.component.button.SolplyBasicButton
import com.teamsolply.solply.designsystem.component.chip.CheckedCircle
import com.teamsolply.solply.designsystem.component.textfield.SolplyFixedReportTextField
import com.teamsolply.solply.designsystem.component.textfield.SolplyRenameCourseTextField
import com.teamsolply.solply.designsystem.theme.SolplyTheme
import com.teamsolply.solply.maps.model.ReportType
import com.teamsolply.solply.ui.extension.addFocusCleaner
import com.teamsolply.solply.ui.extension.customClickable
import kotlinx.coroutines.launch

@Composable
fun ReportPlaceDialog(
    onDismissRequest: () -> Unit,
    selectedReportType: ReportType,
    reportContent: String,
    onReportTypeClick: (ReportType) -> Unit,
    inputReportContent: (String) -> Unit,
) {
    val coroutineScope = rememberCoroutineScope()
    val pagerState = rememberPagerState(initialPage = 0, pageCount = { 3 })

    Dialog(
        onDismissRequest = onDismissRequest,
        properties = DialogProperties(
            dismissOnBackPress = true,
            dismissOnClickOutside = false,
            usePlatformDefaultWidth = false,
            decorFitsSystemWindows = false
        )
    ) {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = SolplyTheme.colors.white,
            shadowElevation = 32.dp
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                Row(
                    modifier = Modifier
                        .padding(start = 16.dp, top = 56.dp, bottom = 24.dp)
                        .minimumInteractiveComponentSize()
                        .customClickable(rippleEnabled = false) {
                            //TODO. 백 클릭
                            when (pagerState.currentPage) {
                                0 -> onDismissRequest()
                            }
                        },
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        painter = painterResource(R.drawable.ic_back_long_arrow),
                        contentDescription = "back_arrow",
                        modifier = Modifier.padding(end = 8.dp),
                        tint = SolplyTheme.colors.gray900
                    )
                    Text(
                        text = "제보하기",
                        color = SolplyTheme.colors.black,
                        style = SolplyTheme.typography.head16M
                    )
                }
                HorizontalPager(
                    state = pagerState,
                    userScrollEnabled = false,
                    verticalAlignment = Alignment.Top,
                    modifier = Modifier.fillMaxWidth()
                ) { page ->
                    when (page) {
                        0 -> {
                            ReportTypesScreen(
                                selectedReportType = selectedReportType,
                                onReportTypeClick = onReportTypeClick
                            )
                        }

                        1 -> {
                            ReportContentScreen(
                                reportContent = reportContent,
                                inputReportContent = inputReportContent
                            )
                        }

                        else -> {}
                    }
                }
                Spacer(modifier = Modifier.weight(1f))

                if (pagerState.currentPage != 2) {
                    SolplyBasicButton(
                        text = "다음",
                        onClick = {
                            when (pagerState.currentPage) {
                                0 -> if (selectedReportType != ReportType.EMPTY) {
                                    coroutineScope.launch {
                                        pagerState.animateScrollToPage(1)
                                    }
                                }
                            }
                        },
                        modifier = Modifier.padding(start = 20.dp, end = 20.dp, bottom = 72.dp),
                        selected = selectedReportType != ReportType.EMPTY,
                        enabledBackgroundColor = SolplyTheme.colors.gray900,
                        disabledBackgroundColor = SolplyTheme.colors.gray300,
                    )
                }
            }
        }
    }
}


@Composable
fun ReportTypesScreen(
    selectedReportType: ReportType,
    onReportTypeClick: (ReportType) -> Unit,
) {
    val borderColor = SolplyTheme.colors.gray200

    Column(
        modifier = Modifier
            .padding(horizontal = 20.dp)
            .fillMaxWidth()
    ) {
        ReportType.entries
            .filterNot { it == ReportType.EMPTY }
            .forEach { type ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .drawBehind {
                            val strokeWidth = 1.dp.toPx()
                            val y = size.height - strokeWidth / 2
                            drawLine(
                                color = borderColor,
                                start = Offset(0f, y),
                                end = Offset(size.width, y),
                                strokeWidth = strokeWidth
                            )
                        }
                        .padding(horizontal = 20.dp, vertical = 11.dp)
                        .customClickable(rippleEnabled = false) {
                            onReportTypeClick(type)
                        },
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = type.content,
                        color = SolplyTheme.colors.black,
                        style = SolplyTheme.typography.body16R
                    )
                    if (type == selectedReportType) {
                        CheckedCircle()
                    }
                }
            }
    }
}

@Composable
fun ReportContentScreen(
    reportContent: String,
    inputReportContent: (String) -> Unit,
) {
    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current

    Column(
        modifier = Modifier
            .padding(horizontal = 20.dp)
            .fillMaxWidth()
            .pointerInput(Unit) {
                detectTapGestures(onTap = {
                    focusManager.clearFocus()
                    keyboardController?.hide()
                })
            }
    ) {
        Text(
            text = "잘못된 정보에 대한 설명을 입력해주세요",
            modifier = Modifier.padding(bottom = 12.dp),
            color = SolplyTheme.colors.black,
            style = SolplyTheme.typography.body16R
        )
        SolplyFixedReportTextField(
            value = reportContent,
            onValueChange = { inputReportContent(it) },
        )
        Row(
            modifier = Modifier.padding(top = 28.dp, bottom = 12.dp)
        ) {
            Text(
                text = "관련 자료가 있다면 첨부해주세요",
                modifier = Modifier.padding(end = 4.dp),
                color = SolplyTheme.colors.black,
                style = SolplyTheme.typography.body16M
            )
            Text(
                text = "(선택)",
                color = SolplyTheme.colors.gray500,
                style = SolplyTheme.typography.body16M
            )
        }
        Row {
            Box(
                modifier = Modifier
                    .size(72.dp)
                    .background(color = SolplyTheme.colors.red200),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_cross),
                    contentDescription = "add_picture",
                    tint = SolplyTheme.colors.red600
                )
            }
        }

        //TODO. 텍스트필드 높이 확인. 포커스 확인
        // 사진 피커 완성하기
    }
}