package com.teamsolply.solply.search

import androidx.compose.foundation.focusable
import androidx.compose.foundation.gestures.awaitEachGesture
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.gestures.waitForUpOrCancellation
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.minimumInteractiveComponentSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.input.pointer.PointerEventPass
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.hilt.navigation.compose.hiltViewModel
import com.teamsolply.solply.designsystem.R
import com.teamsolply.solply.designsystem.component.textfield.SolplyRenameCourseTextField
import com.teamsolply.solply.designsystem.theme.SolplyTheme
import com.teamsolply.solply.search.component.SearchItem
import com.teamsolply.solply.ui.extension.clearFocusOnTapOutside
import com.teamsolply.solply.ui.extension.customClickable
import kotlinx.collections.immutable.toImmutableList

@Composable
fun SearchDialog(
    onDismissRequest: () -> Unit,
    navigateToPlaceDetail: (Long, Long) -> Unit,
    navigateToRegisterPlace: () -> Unit,
    viewModel: SearchViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val focusManager = LocalFocusManager.current
    val keyboard = LocalSoftwareKeyboardController.current
    val blankFocus = remember { FocusRequester() }
    val handleDismiss = {
        viewModel.resetSearchState()
        focusManager.clearFocus(force = true)
        keyboard?.hide()
        onDismissRequest()
    }

    Dialog(
        onDismissRequest = handleDismiss,
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
                        .padding(start = 16.dp, top = 56.dp, bottom = 32.dp)
                        .minimumInteractiveComponentSize()
                        .customClickable(rippleEnabled = false) {
                            handleDismiss()
                        }
                ) {
                    Icon(
                        painter = painterResource(R.drawable.ic_back_long_arrow),
                        modifier = Modifier.padding(end = 12.dp),
                        contentDescription = "back_arrow",
                        tint = SolplyTheme.colors.gray900
                    )
                    Text(
                        text = "검색하기",
                        color = SolplyTheme.colors.black,
                        style = SolplyTheme.typography.head16M
                    )
                }
                Box(modifier = Modifier.padding(horizontal = 16.dp)) {
                    SolplyRenameCourseTextField(
                        value = uiState.searchText,
                        onValueChange = { viewModel.sendIntent(SearchIntent.ChangeSearchText(it)) },
                        placeholder = "찾는 장소를 입력하세요"
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.ic_search),
                            contentDescription = "search"
                        )
                    }
                }

                if (uiState.hasQuery && uiState.resultCount > 0) {
                    Text(
                        text = "검색 결과 ${uiState.resultCount}개",
                        style = SolplyTheme.typography.button14M,
                        color = SolplyTheme.colors.gray800,
                        modifier = Modifier.padding(start = 20.dp, top = 32.dp, bottom = 16.dp)
                    )
                    LazyColumn {
                        val items = uiState.searchResult.toImmutableList()
                        itemsIndexed(items) { _, item ->
                            Column {
                                HorizontalDivider(
                                    thickness = 1.dp,
                                    color = SolplyTheme.colors.gray200
                                )
                                SearchItem(
                                    placeName = item.placeName,
                                    placeTag = item.primaryTag,
                                    placeAddress = item.address,
                                    placeImageUrl = item.thumbnailImageUrl,
                                    onClick = {
                                        // TODO. 타운 아이디도 같이
                                        // navigateToPlaceDetail(item.placeId, item.townId)
                                    }
                                )
                            }
                        }
                        item {
                            HorizontalDivider(
                                thickness = 1.dp,
                                color = SolplyTheme.colors.gray200
                            )
                            Row(
                                modifier = Modifier
                                    .padding(
                                        start = 20.dp,
                                        end = 20.dp,
                                        top = 20.dp,
                                        bottom = 250.dp
                                    )
                                    .customClickable(rippleEnabled = false) {
                                        navigateToRegisterPlace()
                                    },
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = "찾는 장소가 없어요",
                                    style = SolplyTheme.typography.button14M,
                                    color = SolplyTheme.colors.gray700
                                )
                                Icon(
                                    painter = painterResource(R.drawable.ic_next_arrow),
                                    contentDescription = "search",
                                    tint = SolplyTheme.colors.gray700
                                )
                            }
                        }
                    }
                } else if (uiState.hasQuery) {
                    Text(
                        text = "검색 결과가 없어요.\n" +
                            "직접 장소 등록을 솔플리에 요청해보세요.",
                        style = SolplyTheme.typography.body16R,
                        color = SolplyTheme.colors.black,
                        modifier = Modifier.padding(start = 20.dp, top = 32.dp, bottom = 17.dp)
                    )
                    Row(
                        modifier = Modifier
                            .padding(horizontal = 20.dp)
                            .customClickable(rippleEnabled = false) {
                                navigateToRegisterPlace()
                            },
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "장소 등록하러 가기",
                            style = SolplyTheme.typography.button14M,
                            color = SolplyTheme.colors.purple600
                        )
                        Icon(
                            painter = painterResource(R.drawable.ic_next_arrow),
                            contentDescription = "search",
                            tint = SolplyTheme.colors.purple600
                        )
                    }
                }
            }
        }
    }
}
