package com.teamsolply.solply.course.favoriteTown

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.teamsolply.solply.course.CourseSideEffect
import com.teamsolply.solply.course.favoriteTown.model.CourseState
import com.teamsolply.solply.course.favoriteTown.repository.FavoriteTownRepository
import com.teamsolply.solply.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(
    private val favoriteRepository: FavoriteTownRepository
) : BaseViewModel<CourseState, FavoriteTownIntent, CourseSideEffect>(CourseState()) {

    init { handleIntent(FavoriteTownIntent.LoadFavoriteTownList) }

    override fun handleIntent(intent: FavoriteTownIntent) {
        when (intent) {
            is FavoriteTownIntent.LoadFavoriteTownList -> loadTownTree()
            is FavoriteTownIntent.SelectTown          -> reduce { copy(selectedTownId = intent.townId) }
            is FavoriteTownIntent.SelectRegion        -> reduce { copy(selectedRegionId = intent.regionId) }
            is FavoriteTownIntent.ConfirmSelection    -> patchUserFavoriteTown()
            is FavoriteTownIntent.OnRetry             -> loadTownTree()
        }
    }

    private fun loadTownTree() {
        viewModelScope.launch {
            favoriteRepository.getTownTree()
                .onSuccess { (regions, townsByRegion) ->
                    reduce {
                        copy(
                            regions = regions,
                            townsByRegion = townsByRegion,
                            selectedRegionId = regions.firstOrNull()?.id,
                            selectedTownId = null
                        )
                    }
                }
                .onFailure { e ->
                    Log.e("FavoriteViewModel", "loadTownTree fail: ${e.message}", e)
                }
        }
    }

    private fun patchUserFavoriteTown() {
        val s = uiState.value
        val selectedTownId = s.selectedTownId ?: return
        val favoriteTownIdList = s.townsByRegion.values.flatten().map { it.id }

        viewModelScope.launch {
            favoriteRepository.patchUserFavoriteTown(selectedTownId, favoriteTownIdList)
                .onSuccess { Log.d("FavoriteViewModel", "패치 성공") }
                .onFailure { e -> Log.e("FavoriteViewModel", "패치 실패 ${e.message}", e) }
        }
    }
}
