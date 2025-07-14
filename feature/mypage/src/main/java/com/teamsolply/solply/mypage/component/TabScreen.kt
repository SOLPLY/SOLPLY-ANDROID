package com.teamsolply.solply.mypage.component

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.teamsolply.solply.designsystem.theme.SolplyTheme
import com.teamsolply.solply.mypage.model.MypageTab
import com.teamsolply.solply.mypage.model.TownCard
import com.teamsolply.solply.ui.preview.DefaultPreview
import okhttp3.internal.immutableListOf

@Composable
fun TabScreen(
    onClickEmptyButton: (MypageTab) -> Unit,
    onClickTown: (String) -> Unit,
    town: List<TownCard>,
    mypageTab: MypageTab,
    modifier: Modifier = Modifier
) {
    if (town.isEmpty()) {
        EmptyCollectionScreen(
            onClick = { onClickEmptyButton(mypageTab) },
            mypageTab = mypageTab,
            modifier = modifier
        )
    } else {
        when (mypageTab) {
            MypageTab.PLACE ->
                TownCollectionScreen(
                    town = town,
                    onClickTown = onClickTown,
                    mypageTab = mypageTab,
                    modifier = modifier
                )

            MypageTab.COURSE ->
                TownCollectionScreen(
                    town = town,
                    onClickTown = onClickTown,
                    mypageTab = mypageTab,
                    modifier = modifier
                )
        }
    }
}

@DefaultPreview
@Composable
private fun PlaceTabScreenPreview() {
    SolplyTheme {
        TabScreen(
            onClickEmptyButton = {},
            town = immutableListOf(
                TownCard(
                    townName = "연희동",
                    imageUrl = ""
                ),
                TownCard(
                    townName = "망원동",
                    imageUrl = ""
                )
            ),
            onClickTown = {},
            mypageTab = MypageTab.COURSE
        )
    }
}
