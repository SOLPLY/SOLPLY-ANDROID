package com.teamsolply.solply.registerplace

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.teamsolply.solply.designsystem.R
import com.teamsolply.solply.designsystem.component.textfield.SolplyRenameCourseTextField
import com.teamsolply.solply.designsystem.theme.SolplyTheme
import com.teamsolply.solply.model.PlaceType
import com.teamsolply.solply.registerplace.component.PlaceTypeDropDown
import com.teamsolply.solply.registerplace.component.RegisterPlaceItem
import com.teamsolply.solply.ui.extension.addFocusCleaner
import com.teamsolply.solply.ui.extension.customClickable
import kotlinx.collections.immutable.toImmutableList

@Composable
fun RegisterPlaceRoute(
    paddingValues: PaddingValues,
    navigateToBack: () -> Unit,
    viewModel: RegisterPlaceViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

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
        }
    )
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
) {
    val focusManager = LocalFocusManager.current
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
            .background(color = SolplyTheme.colors.white)
            .addFocusCleaner(focusManager)
    ) {
        Row(
            modifier = Modifier
                .padding(start = 16.dp, top = 16.dp, bottom = 32.dp)
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
        Column(modifier = Modifier.padding(horizontal = 16.dp)) {
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

        if (!uiState.isPlaceNameSuccess && uiState.hasQuery && uiState.resultCount > 0) {
            Text(
                text = "검색 결과 ${uiState.resultCount}개",
                style = SolplyTheme.typography.button14M,
                color = SolplyTheme.colors.gray800,
                modifier = Modifier.padding(start = 20.dp, top = 32.dp, bottom = 16.dp)
            )
            LazyColumn {
                val items = uiState.searchResults.toImmutableList()
                itemsIndexed(items) { _, item ->
                    Column {
                        HorizontalDivider(
                            thickness = 1.dp,
                            color = SolplyTheme.colors.gray200
                        )
                        RegisterPlaceItem(
                            placeName = item.title,
                            placeAddress = item.address,
                            onClick = selectPlaceName
                        )
                    }
                }
                item {
                    HorizontalDivider(
                        thickness = 1.dp,
                        color = SolplyTheme.colors.gray200
                    )
                }
            }
        }

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

        if (uiState.selectedPlaceType != null && !uiState.isPlaceTypeDropdownExpanded) {
            Text("asd")
        }
    }
}