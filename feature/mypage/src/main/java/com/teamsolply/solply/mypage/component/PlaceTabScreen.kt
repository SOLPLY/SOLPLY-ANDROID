package com.teamsolply.solply.mypage.component

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.teamsolply.solply.designsystem.theme.SolplyTheme
import com.teamsolply.solply.mypage.model.MypageTab
import com.teamsolply.solply.mypage.model.PlaceCard
import com.teamsolply.solply.mypage.model.TownCard
import com.teamsolply.solply.ui.preview.DefaultPreview
import okhttp3.internal.immutableListOf

@Composable
fun PlaceTabScreen(
    isTownSelected: Boolean,
    onClickEmptyButton: () -> Unit,
    onClickTown: (String) -> Unit,
    town: List<TownCard>,
    place: List<PlaceCard>,
    modifier: Modifier = Modifier
) {

    if (town.isEmpty()) {
        EmptyCollectionScreen(
            onClick = onClickEmptyButton,
            mypageTab = MypageTab.PLACE
        )
    } else {
        if (isTownSelected) {
            PlaceCollectionScreen(
                place = place
            )
        } else {
            TownCollectionScreen(
                town = town,
                onClickTown = onClickTown
            )
        }
    }
}


@DefaultPreview
@Composable
private fun PlaceTabScreenPreview() {
    SolplyTheme {
        PlaceTabScreen(
            isTownSelected = false,
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
            place = emptyList()
        )
    }
}
