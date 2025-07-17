package com.teamsolply.solply.course.favoriteTown

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.teamsolply.solply.course.CourseSideEffect
import com.teamsolply.solply.course.favoriteTown.repository.FavoriteTownRepository
import com.teamsolply.solply.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(
    private val favoriteRepository: FavoriteTownRepository
) : BaseViewModel<FavoriteTownState, FavoriteTownIntent, CourseSideEffect>(
    FavoriteTownState()
) {

    init {
        handleIntent(FavoriteTownIntent.LoadFavoriteTownList)
    }

    override fun handleIntent(intent: FavoriteTownIntent) {
        when (intent) {
            is FavoriteTownIntent.LoadFavoriteTownList -> getTownList()

            is FavoriteTownIntent.SelectTown -> {
                reduce {
                    copy(
                        townInfo = townInfo.copy(
                            selectedTown = townInfo.favoriteTownList.find { it.townId == intent.townId }
                        )
                    )
                }
            }

            is FavoriteTownIntent.ConfirmSelection -> patchUserFavoriteTown()

            is FavoriteTownIntent.OnRetry -> getTownList()
        }
    }


    private fun getTownList() {
        viewModelScope.launch {
            favoriteRepository.getTownList().onSuccess {
                reduce {
                    copy(
                        townInfo = it
                    )
                }
            }.onFailure { exception ->
                Log.e("FavoriteViewModel", "실패 이유: ${exception.message}", exception)
            }
        }
    }

    private fun patchUserFavoriteTown() {
        val currentState = uiState.value
        val selectedTownId = currentState.townInfo.selectedTown?.townId ?: return
        val favoriteTownIdList = currentState.townInfo.favoriteTownList.map { it.townId }

        viewModelScope.launch {
            favoriteRepository.patchUserFavoriteTown(
                selectedTownId = selectedTownId,
                favoriteTownIdList = favoriteTownIdList
            ).onSuccess {
                Log.d("FavoriteViewModel", "패치 성공")
            }.onFailure {
                Log.e("FavoriteViewModel", "패치 실패 ${it.message}", it)
            }
        }
    }
}