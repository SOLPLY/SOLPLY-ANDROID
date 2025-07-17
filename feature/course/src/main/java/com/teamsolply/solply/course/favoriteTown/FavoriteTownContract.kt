package com.teamsolply.solply.course.favoriteTown

import com.teamsolply.solply.course.favoriteTown.model.FavoriteTownInfoEntity
import com.teamsolply.solply.course.favoriteTown.model.Town
import com.teamsolply.solply.ui.base.SideEffect
import com.teamsolply.solply.ui.base.UiIntent
import com.teamsolply.solply.ui.base.UiState

data class FavoriteTownState(
    val isLoading: Boolean = false,
    val townInfo: FavoriteTownInfoEntity = FavoriteTownInfoEntity(
        selectedTown = Town(
            townId = 0,
            townName = ""
        ),
        favoriteTownList = emptyList()
    ),
    val errorMessage: String? = null
) : UiState


sealed interface FavoriteTownIntent : UiIntent {
    object LoadFavoriteTownList : FavoriteTownIntent
    data class SelectTown(val townId: Int) : FavoriteTownIntent
    object OnRetry : FavoriteTownIntent
    object ConfirmSelection : FavoriteTownIntent
}

sealed interface CourseSideEffect : SideEffect {
}