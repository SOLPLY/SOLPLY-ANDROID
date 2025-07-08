package com.teamsolply.solply.mypage

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.teamsolply.solply.designsystem.component.button.SolplyBasicButton
import com.teamsolply.solply.designsystem.theme.SolplyTheme
import com.teamsolply.solply.model.MapsType
import com.teamsolply.solply.mypage.component.MypageTopBar
import com.teamsolply.solply.ui.extension.customClickable
import com.teamsolply.solply.ui.preview.DefaultPreview

@Composable
fun MypageRoute(
    paddingValues: PaddingValues,
    navigateToMaps: (String) -> Unit
) {
    MypageScreen(
        navigateToMaps = navigateToMaps
    )
}

@Composable
fun MypageScreen(
    navigateToMaps: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val pagerState = rememberPagerState(pageCount = { 2 })
    Column(
        modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        MypageTopBar(
            title = "수집함",
            onBackButtonClick = {}
        )
        HorizontalPager(
            modifier = Modifier
                .fillMaxSize(),
            state = pagerState
        ) { page ->
            // Our page content
            Text(
                text = "Page: $page",
                modifier = Modifier.fillMaxWidth()
            )

        }
        Text(
            text = "Mypage",
            modifier = Modifier.customClickable { navigateToMaps(MapsType.EDIT_COURSE.name) }
        )
    }
}

@DefaultPreview
@Composable
private fun MypageScreenPreview() {
    SolplyTheme {
        MypageScreen(
            navigateToMaps = {}
        )
    }
}

@Composable
private fun PlaceCollectionScreen(
    onClickEmptyButton: () -> Unit,
    modifier: Modifier = Modifier,
    isEmpty: Boolean = true
) {
    if (isEmpty) {
        EmptyCollectionScreen(
            onClick = onClickEmptyButton
        )
    } else {
        LazyColumn {

        }
    }
}

@Composable
private fun EmptyCollectionScreen(
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "저장한 장소가 없어요.",
            style = SolplyTheme.typography.body16M,
            color = SolplyTheme.colors.gray800
        )
        Spacer(
            modifier = Modifier
                .height(28.dp)
        )
        SolplyBasicButton(
            text = "나만의 장소 수집하러 가기",
            onClick = onClick,
            textColor = SolplyTheme.colors.purple700,
            enabledBackgroundColor = SolplyTheme.colors.purple300,
            modifier = Modifier.padding(horizontal = 64.dp)
        )
    }
}

@DefaultPreview
@Composable
private fun PlaceCollectionScreenPreview() {
    SolplyTheme {
        PlaceCollectionScreen(
            onClickEmptyButton = {}
        )
    }
}