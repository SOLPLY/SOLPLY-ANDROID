package com.teamsolply.solply.registerplace

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.relocation.BringIntoViewRequester
import androidx.compose.foundation.relocation.bringIntoViewRequester
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.teamsolply.solply.designsystem.R
import com.teamsolply.solply.designsystem.component.button.SolplyBasicButton
import com.teamsolply.solply.designsystem.component.card.RegisterPlaceImage
import com.teamsolply.solply.designsystem.component.textfield.SolplyFixedReportTextField
import com.teamsolply.solply.designsystem.component.textfield.SolplyRenameCourseTextField
import com.teamsolply.solply.designsystem.theme.SolplyTheme
import com.teamsolply.solply.model.PlaceType
import com.teamsolply.solply.registerplace.component.PlaceTypeDropDown
import com.teamsolply.solply.registerplace.component.RegisterPlaceItem
import com.teamsolply.solply.ui.extension.addFocusCleaner
import com.teamsolply.solply.ui.extension.customClickable
import com.teamsolply.solply.ui.lifecycle.LaunchedEffectWithLifecycle
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@Composable
fun RegisterPlaceRoute(
    paddingValues: PaddingValues,
    navigateToBack: () -> Unit,
    viewModel: RegisterPlaceViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffectWithLifecycle {
        viewModel.sideEffect.collectLatest { sideEffect ->
            when (sideEffect) {
                RegisterPlaceSideEffect.NavigateToBack -> navigateToBack()
            }
        }
    }

    RegisterPlaceScreen(
        uiState = uiState,
        paddingValues = paddingValues,
        navigateToBack = navigateToBack,
        inputPlaceNameText = { text ->
            viewModel.sendIntent(
                RegisterPlaceIntent.InputPlaceNameText(
                    text
                )
            )
        },

        // 장소 이름
        selectPlaceName = { placeName, placeAddress ->
            viewModel.sendIntent(
                RegisterPlaceIntent.SelectPlaceName(
                    placeName = placeName,
                    placeAddress = placeAddress
                )
            )
        },

        // 장소 유형
        selectedPlaceType = uiState.selectedPlaceType,
        isPlaceTypeDropdownExpanded = uiState.isPlaceTypeDropdownExpanded,
        changePlaceTypeDropDownVisible = { viewModel.sendIntent(RegisterPlaceIntent.ChangePlaceTypeDropDownVisible) },
        clickDropDownItem = { placeType ->
            viewModel.sendIntent(RegisterPlaceIntent.ClickDropDownItem(placeType = placeType))
        },
        selectedPlaceKeyword = uiState.selectedPlaceKeyword,
        clickPlaceKeyword = { id ->
            viewModel.sendIntent(RegisterPlaceIntent.ClickPlaceKeyword(placeKeywordId = id))
        },
        selectedPlaceFeature = uiState.selectedPlaceFeature,
        clickPlaceFeature = { id ->
            viewModel.sendIntent(RegisterPlaceIntent.ClickPlaceFeature(placeFeatureId = id))
        },
        registerPlaceReason = uiState.registerPlaceReason,
        inputRegisterPlaceReason = { text ->
            viewModel.sendIntent(RegisterPlaceIntent.InputRegisterPlaceReason(text = text))
        },
        selectedUris = uiState.selectedReportUris,
        onSelectUris = { uris ->
            viewModel.sendIntent(RegisterPlaceIntent.SelectedReportUris(uris = uris))
        },
        clickRegisterPlace = { viewModel.sendIntent(RegisterPlaceIntent.ClickRegisterPlace) },
        resetSelectedUris = { index ->
            viewModel.sendIntent(RegisterPlaceIntent.ResetSelectedUris(index = index))
        }
    )

    if (uiState.registerLottieVisibility) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(SolplyTheme.colors.white),
            contentAlignment = Alignment.Center
        ) {
            RegisterPlaceSubmittingScreen(isSuccess = uiState.isRegisterSuccess)
        }
    }
}

@Composable
fun RegisterPlaceScreen(
    uiState: RegisterPlaceState,
    paddingValues: PaddingValues,
    navigateToBack: () -> Unit,
    inputPlaceNameText: (String) -> Unit,
    // 장소 이름
    selectPlaceName: (String, String) -> Unit,

    // 장소 유형
    selectedPlaceType: PlaceType?,
    isPlaceTypeDropdownExpanded: Boolean,
    changePlaceTypeDropDownVisible: () -> Unit,
    clickDropDownItem: (PlaceType) -> Unit,

    // 장소 키워드
    selectedPlaceKeyword: List<Long>,
    clickPlaceKeyword: (Long) -> Unit,

    // 장소 특징
    selectedPlaceFeature: List<Long>,
    clickPlaceFeature: (Long) -> Unit,
    // 장소 이유
    registerPlaceReason: String,
    inputRegisterPlaceReason: (String) -> Unit,
    // 장소 사진
    selectedUris: List<Uri>,
    onSelectUris: (List<Uri>) -> Unit,
    clickRegisterPlace: () -> Unit,
    resetSelectedUris: (Int) -> Unit,
) {
    val focusManager = LocalFocusManager.current
    val lazyListState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()
    val bringIntoViewRequester = remember { BringIntoViewRequester() }

    val placeKeywords = listOf(
        PlaceKeywordItem(7L, "커피/디저트"),
        PlaceKeywordItem(8L, "작업"),
        PlaceKeywordItem(9L, "독서"),
        PlaceKeywordItem(10L, "힐링")
    )
    val placeFeatures = listOf(
        PlaceKeywordItem(11L, "시그니처 메뉴"),
        PlaceKeywordItem(12L, "감성 인테리어"),
        PlaceKeywordItem(13L, "콘센트 많음"),
        PlaceKeywordItem(14L, "시간제한 없음"),
        PlaceKeywordItem(15L, "채광 좋음"),
        PlaceKeywordItem(16L, "바테이블")
    )
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
            .fillMaxSize()
            .padding(paddingValues)
            .background(color = SolplyTheme.colors.white)
            .addFocusCleaner(focusManager)
            .imePadding()
    ) {
        Row(
            modifier = Modifier
                .padding(start = 16.dp, top = 16.dp, bottom = 16.dp)
                .customClickable(rippleEnabled = false) {
                    navigateToBack()
                }
        ) {
            Icon(
                painter = painterResource(R.drawable.ic_back_long_arrow),
                modifier = Modifier.padding(end = 12.dp),
                contentDescription = "back_arrow",
                tint = SolplyTheme.colors.gray900
            )
            Text(
                text = "장소 등록하기",
                color = SolplyTheme.colors.black,
                style = SolplyTheme.typography.head16M
            )
        }
        LazyColumn(
            modifier = Modifier
                .fillMaxSize(),
            state = lazyListState
        ) {
            item {
                Column(modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 16.dp)) {
                    Text(
                        text = "장소 이름",
                        modifier = Modifier.padding(bottom = 12.dp),
                        color = SolplyTheme.colors.black,
                        style = SolplyTheme.typography.body16M
                    )
                    if (uiState.isPlaceNameSuccess) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .border(
                                    width = 1.dp,
                                    color = SolplyTheme.colors.gray300,
                                    shape = RoundedCornerShape(20.dp)
                                )
                                .padding(horizontal = 20.dp, vertical = 14.dp)
                                .customClickable(rippleEnabled = false) {
                                    selectPlaceName(uiState.placeName, "")
                                }
                        ) {
                            Text(
                                text = uiState.placeName,
                                style = SolplyTheme.typography.body16R,
                                color = SolplyTheme.colors.black
                            )
                            if (uiState.placeAddress.isNotEmpty()) {
                                Text(
                                    text = uiState.placeAddress,
                                    modifier = Modifier.padding(top = 4.dp),
                                    style = SolplyTheme.typography.caption12R,
                                    color = SolplyTheme.colors.gray700
                                )
                            }
                        }
                    } else {
                        SolplyRenameCourseTextField(
                            value = uiState.placeName,
                            onValueChange = inputPlaceNameText,
                            placeholder = "장소 이름을 입력하세요",
                            innerPadding = PaddingValues(horizontal = 20.dp, vertical = 14.dp),
                            trailingIcon = {
                                Text(
                                    text = "직접 등록",
                                    modifier = Modifier.customClickable(false) {
                                        if (uiState.placeName.length > 1) {
                                            selectPlaceName(uiState.placeName, "")
                                        }
                                    },
                                    style = SolplyTheme.typography.body14R.copy(
                                        textDecoration = TextDecoration.Underline
                                    ),
                                    color = SolplyTheme.colors.purple600
                                )
                            }
                        )
                    }
                }
            }

            item {
                if (!uiState.isPlaceNameSuccess && uiState.hasQuery && uiState.resultCount > 0) {
                    Text(
                        text = "검색 결과 ${uiState.resultCount}개",
                        style = SolplyTheme.typography.button14M,
                        color = SolplyTheme.colors.gray800,
                        modifier = Modifier.padding(start = 20.dp, top = 32.dp, bottom = 16.dp)
                    )
                    HorizontalDivider(
                        thickness = 1.dp,
                        color = SolplyTheme.colors.gray200
                    )
                }
            }
            if (!uiState.isPlaceNameSuccess && uiState.hasQuery && uiState.resultCount > 0) {
                val items = uiState.searchResults.toImmutableList()
                itemsIndexed(items) { _, item ->
                    RegisterPlaceItem(
                        placeName = item.title,
                        placeAddress = item.address,
                        onClick = selectPlaceName
                    )
                    HorizontalDivider(
                        thickness = 1.dp,
                        color = SolplyTheme.colors.gray200
                    )
                }
            }

            item {
                if (uiState.isPlaceNameSuccess) {
                    Text(
                        text = "장소 유형",
                        modifier = Modifier.padding(start = 20.dp, top = 40.dp, bottom = 12.dp),
                        color = SolplyTheme.colors.black,
                        style = SolplyTheme.typography.body16M
                    )
                    PlaceTypeDropDown(
                        placeholder = "장소 유형을 선택해주세요.",
                        onClickItem = clickDropDownItem,
                        onClickDropIcon = changePlaceTypeDropDownVisible,
                        dropDownContents = PlaceType.entries.filterNot { it == PlaceType.ALL },
                        selectedPlaceType = selectedPlaceType,
                        modifier = Modifier.padding(horizontal = 20.dp),
                        isDropped = isPlaceTypeDropdownExpanded
                    )
                }
            }

            item {
                if (uiState.selectedPlaceType != null && !uiState.isPlaceTypeDropdownExpanded) {
                    Text(
                        text = "장소와 어울리는 키워드를 골라주세요",
                        modifier = Modifier.padding(start = 20.dp, top = 40.dp, bottom = 12.dp),
                        color = SolplyTheme.colors.black,
                        style = SolplyTheme.typography.body16M
                    )
                    FlowRow(
                        modifier = Modifier.padding(horizontal = 20.dp),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        placeKeywords.forEach { keyword ->
                            PlaceKeyWord(
                                text = keyword.text,
                                isSelected = selectedPlaceKeyword.contains(keyword.id),
                                onClick = {
                                    clickPlaceKeyword(keyword.id)
                                }
                            )
                        }
                    }
                    Text(
                        text = "장소의 특징을 선택해주세요",
                        modifier = Modifier.padding(start = 20.dp, top = 40.dp, bottom = 12.dp),
                        color = SolplyTheme.colors.black,
                        style = SolplyTheme.typography.body16M
                    )
                    FlowRow(
                        modifier = Modifier.padding(horizontal = 20.dp),
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        placeFeatures.forEach { keyword ->
                            PlaceKeyWord(
                                text = keyword.text,
                                isSelected = selectedPlaceFeature.contains(keyword.id),
                                onClick = {
                                    clickPlaceFeature(keyword.id)
                                }
                            )
                        }
                    }
                    Row(
                        modifier = Modifier.padding(start = 20.dp, top = 40.dp, bottom = 12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "장소를 추천하는 이유를 작성해주세요",
                            color = SolplyTheme.colors.black,
                            style = SolplyTheme.typography.body16M
                        )
                        Text(
                            text = "(선택)",
                            modifier = Modifier.padding(start = 4.dp),
                            color = SolplyTheme.colors.gray500,
                            style = SolplyTheme.typography.body16M
                        )
                    }
                    SolplyFixedReportTextField(
                        value = registerPlaceReason,
                        onValueChange = inputRegisterPlaceReason,
                        onFocusChanged = { isFocused ->
                            if (isFocused) {
                                coroutineScope.launch {
                                    kotlinx.coroutines.delay(200)
                                    bringIntoViewRequester.bringIntoView()
                                }
                            }
                        },
                        modifier = Modifier
                            .padding(horizontal = 20.dp)
                            .bringIntoViewRequester(bringIntoViewRequester)
                    )
                    Row(
                        modifier = Modifier.padding(start = 20.dp, top = 40.dp, bottom = 12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "장소의 사진이 있다면 추가해주세요",
                            color = SolplyTheme.colors.black,
                            style = SolplyTheme.typography.body16M
                        )
                        Text(
                            text = "(선택)",
                            modifier = Modifier.padding(start = 4.dp),
                            color = SolplyTheme.colors.gray500,
                            style = SolplyTheme.typography.body16M
                        )
                    }
                    RegisterPlaceImage(
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
                        },
                        resetSelectedUris = resetSelectedUris,
                        modifier = Modifier.padding(start = 20.dp, end = 20.dp, bottom = 76.dp),
                    )
                    SolplyBasicButton(
                        text = "완료",
                        onClick = clickRegisterPlace,
                        modifier = Modifier.padding(start = 20.dp, end = 20.dp, bottom = 72.dp),
                        selected = uiState.placeName.isNotEmpty() && uiState.selectedPlaceType != null && uiState.selectedPlaceKeyword.isNotEmpty() && uiState.selectedPlaceFeature.isNotEmpty(),
                        enabledBackgroundColor = SolplyTheme.colors.gray900,
                        disabledBackgroundColor = SolplyTheme.colors.gray300
                    )
                }
            }
        }
    }
}

@Composable
private fun RegisterPlaceSubmittingScreen(isSuccess: Boolean) {
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.finish_onboarding))
    val progress by animateLottieCompositionAsState(
        composition = composition,
        iterations = LottieConstants.IterateForever,
        isPlaying = true
    )
    val targetAlpha = remember(progress) {
        1f - ((progress - 0.6f) / 0.4f).coerceIn(0f, 1f)
    }
    val textAlpha by animateFloatAsState(targetValue = targetAlpha, label = "textAlpha")

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(182.dp))
        LottieAnimation(
            composition = composition,
            progress = { progress },
            modifier = Modifier
                .size(270.dp)
        )
        Text(
            text = "소중한 제보 감사합니다!",
            modifier = Modifier.graphicsLayer { alpha = textAlpha },
            color = SolplyTheme.colors.black,
            style = SolplyTheme.typography.display20Sb
        )
    }
}

@Composable
fun PlaceKeyWord(
    text: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    val (textColor, backgroundColor, borderColor) = if (isSelected) {
        Triple(
            SolplyTheme.colors.white,
            SolplyTheme.colors.gray900,
            SolplyTheme.colors.gray900
        )
    } else {
        Triple(
            SolplyTheme.colors.gray900,
            SolplyTheme.colors.white,
            SolplyTheme.colors.gray300
        )
    }

    Box(
        modifier = Modifier
            .background(
                color = backgroundColor,
                shape = CircleShape
            )
            .border(
                width = 1.dp,
                shape = CircleShape,
                color = borderColor
            )
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .customClickable(rippleEnabled = false) { onClick() }
    ) {
        Text(
            text = text,
            style = SolplyTheme.typography.body16M,
            color = textColor
        )
    }
}

data class PlaceKeywordItem(
    val id: Long,
    val text: String
)
