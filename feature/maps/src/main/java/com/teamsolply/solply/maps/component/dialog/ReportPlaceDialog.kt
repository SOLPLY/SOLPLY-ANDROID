package com.teamsolply.solply.maps.component.dialog

import android.content.ContentResolver
import android.net.Uri
import android.provider.OpenableColumns
import android.webkit.MimeTypeMap
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.focusable
import androidx.compose.foundation.gestures.awaitEachGesture
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.gestures.waitForUpOrCancellation
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.minimumInteractiveComponentSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.PointerEventPass
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.teamsolply.solply.designsystem.R
import com.teamsolply.solply.designsystem.component.button.SolplyBasicButton
import com.teamsolply.solply.designsystem.component.chip.CheckedCircle
import com.teamsolply.solply.designsystem.component.textfield.SolplyFixedReportTextField
import com.teamsolply.solply.designsystem.theme.SolplyTheme
import com.teamsolply.solply.maps.model.ReportType
import com.teamsolply.solply.ui.extension.clearFocusOnTapOutside
import com.teamsolply.solply.ui.extension.customClickable
import com.teamsolply.solply.ui.image.AdaptationImage
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun ReportPlaceDialog(
    onDismissRequest: (Boolean) -> Unit,
    selectedReportType: ReportType,
    reportContent: String,
    onReportTypeClick: (ReportType) -> Unit,
    inputReportContent: (String) -> Unit,
    selectedUris: List<Uri>,
    onSelectUris: (List<Uri>) -> Unit,
    reportWrongPlace: (List<String>) -> Unit,
    reportLottieVisibility: Boolean
) {
    val coroutineScope = rememberCoroutineScope()
    val pagerState = rememberPagerState(initialPage = 0, pageCount = { 2 })
    val focusManager = LocalFocusManager.current
    val keyboard = LocalSoftwareKeyboardController.current
    val blankFocus = remember { FocusRequester() }
    val context = LocalContext.current

    Dialog(
        onDismissRequest = {
            focusManager.clearFocus(force = true)
            keyboard?.hide()
            onDismissRequest(false)
        },
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
            Box(modifier = Modifier.fillMaxSize()) {
                if (reportLottieVisibility) {
                    ReportSubmittingScreen(
                        onFinished = { onDismissRequest(false) }
                    )
                } else {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .focusRequester(blankFocus)
                            .focusable()
                            .clearFocusOnTapOutside(
                                focusManager = focusManager,
                                keyboard = keyboard
                            )
                            .pointerInput(Unit) {
                                awaitEachGesture {
                                    awaitFirstDown(
                                        requireUnconsumed = false,
                                        pass = PointerEventPass.Final
                                    )
                                    blankFocus.requestFocus()
                                    keyboard?.hide()
                                    waitForUpOrCancellation()
                                }
                            }
                    ) {
                        Row(
                            modifier = Modifier
                                .padding(start = 16.dp, top = 56.dp, bottom = 24.dp)
                                .minimumInteractiveComponentSize()
                                .customClickable(rippleEnabled = false) {
                                    // TODO. 백 클릭
                                    when (pagerState.currentPage) {
                                        0 -> onDismissRequest(false)
                                        1 -> coroutineScope.launch {
                                            pagerState.animateScrollToPage(0)
                                        }
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
                                        selectedUris = selectedUris,
                                        reportContent = reportContent,
                                        inputReportContent = inputReportContent,
                                        onSelectUris = onSelectUris
                                    )
                                }
                            }
                        }
                        Spacer(modifier = Modifier.weight(1f))

                        SolplyBasicButton(
                            text = "다음",
                            onClick = {
                                when (pagerState.currentPage) {
                                    0 -> if (selectedReportType != ReportType.EMPTY) {
                                        coroutineScope.launch {
                                            pagerState.animateScrollToPage(1)
                                        }
                                    }

                                    1 -> {
                                        if (reportContent.isNotEmpty()) {
                                            reportWrongPlace(
                                                selectedUris.map {
                                                    context.contentResolver.getFileName(
                                                        uri = it
                                                    )
                                                }
                                            )
                                        }
                                    }
                                }
                            },
                            modifier = Modifier.padding(start = 20.dp, end = 20.dp, bottom = 72.dp),
                            selected = when (pagerState.currentPage) {
                                0 -> selectedReportType != ReportType.EMPTY
                                1 -> reportContent.isNotEmpty()
                                else -> false
                            },
                            enabledBackgroundColor = SolplyTheme.colors.gray900,
                            disabledBackgroundColor = SolplyTheme.colors.gray300
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun ReportTypesScreen(
    selectedReportType: ReportType,
    onReportTypeClick: (ReportType) -> Unit
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
    selectedUris: List<Uri>,
    reportContent: String,
    inputReportContent: (String) -> Unit,
    onSelectUris: (List<Uri>) -> Unit
) {
    val remainSelectedUris = (3 - selectedUris.size).coerceAtLeast(0)
    val singlePicker = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia()
    ) { uri ->
        if (uri != null) onSelectUris(listOf(uri))
    }
    val multiPicker = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickMultipleVisualMedia(
            maxItems = remainSelectedUris.coerceAtLeast(
                2
            )
        )
    ) { uris ->
        if (uris.isNotEmpty()) onSelectUris(uris.take(remainSelectedUris))
    }

    Column(
        modifier = Modifier
            .padding(horizontal = 20.dp)
            .fillMaxWidth()
    ) {
        Text(
            text = "잘못된 정보에 대한 설명을 입력해주세요",
            modifier = Modifier.padding(bottom = 12.dp),
            color = SolplyTheme.colors.black,
            style = SolplyTheme.typography.body16R
        )
        SolplyFixedReportTextField(
            value = reportContent,
            onValueChange = { inputReportContent(it) }
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

        ReportPlaceImage(
            selectedUris = selectedUris,
            onAddClick = {
                when {
                    remainSelectedUris >= 2 -> {
                        multiPicker.launch(
                            PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                        )
                    }

                    remainSelectedUris == 1 -> {
                        singlePicker.launch(
                            PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                        )
                    }

                    else -> Unit
                }
            }
        )
    }
}

@Composable
fun ReportPlaceImage(
    selectedUris: List<Uri>,
    onAddClick: () -> Unit
) {
    Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
        repeat(3) { index ->
            when {
                index < selectedUris.size -> {
                    Box(
                        modifier = Modifier
                            .size(72.dp)
                            .clip(RoundedCornerShape(16.dp))
                            .background(SolplyTheme.colors.gray100),
                        contentAlignment = Alignment.Center
                    ) {
                        AdaptationImage(
                            imageUrl = selectedUris[index].toString(),
                            contentDescription = "selected image",
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.Crop
                        )
                    }
                }

                index == selectedUris.size && selectedUris.size < 3 -> {
                    Box(
                        modifier = Modifier
                            .size(72.dp)
                            .background(
                                color = SolplyTheme.colors.red200,
                                shape = RoundedCornerShape(16.dp)
                            )
                            .customClickable(rippleEnabled = false) {
                                onAddClick()
                            },
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.ic_cross),
                            contentDescription = "add_picture",
                            tint = SolplyTheme.colors.red600
                        )
                    }
                }

                else -> {
                    val borderColor = SolplyTheme.colors.gray500
                    Box(
                        modifier = Modifier
                            .size(72.dp)
                            .drawBehind {
                                val strokeWidth = 2.dp.toPx()
                                val corner = 16.dp.toPx()
                                val dash = floatArrayOf(8.dp.toPx(), 4.dp.toPx())
                                val inset = strokeWidth / 2f
                                drawRoundRect(
                                    color = borderColor,
                                    topLeft = Offset(inset, inset),
                                    size = Size(
                                        size.width - strokeWidth,
                                        size.height - strokeWidth
                                    ),
                                    cornerRadius = CornerRadius(corner, corner),
                                    style = Stroke(
                                        width = strokeWidth,
                                        pathEffect = PathEffect.dashPathEffect(dash)
                                    )
                                )
                            }
                    )
                }
            }
        }
    }
}

@Composable
private fun ReportSubmittingScreen(
    onFinished: () -> Unit
) {
    LaunchedEffect(Unit) {
        delay(2500)
        onFinished()
    }

    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.finish_onboarding))
    val progress by animateLottieCompositionAsState(composition)
    val targetAlpha = remember(progress) {
        1f - ((progress - 0.6f) / 0.4f).coerceIn(0f, 1f)
    }
    val textAlpha by animateFloatAsState(targetValue = targetAlpha, label = "textAlpha")

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        LottieAnimation(
            composition = composition,
            progress = { progress },
            modifier = Modifier
                .size(270.dp)
                .padding(bottom = 39.dp)
        )
        Text(
            text = "소중한 제보 감사합니다!",
            modifier = Modifier.graphicsLayer { alpha = textAlpha },
            color = SolplyTheme.colors.black,
            style = SolplyTheme.typography.display20Sb
        )
    }
}

fun ContentResolver.getFileName(uri: Uri): String {
    query(uri, arrayOf(OpenableColumns.DISPLAY_NAME), null, null, null)?.use { cursor ->
        val idx = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME)
        if (idx >= 0 && cursor.moveToFirst()) {
            val name = cursor.getString(idx)
            if (!name.isNullOrBlank()) return name
        }
    }
    uri.path?.substringAfterLast('/')?.takeIf { it.isNotBlank() && it.contains('.') }
        ?.let { return it }
    val mime = getType(uri)
    val ext = MimeTypeMap.getSingleton().getExtensionFromMimeType(mime) ?: "jpg"
    return "image_${System.currentTimeMillis()}.$ext"
}
