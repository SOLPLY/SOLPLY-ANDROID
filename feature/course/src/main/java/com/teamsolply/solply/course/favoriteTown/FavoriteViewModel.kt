package com.teamsolply.solply.course.favoriteTown

import android.util.Log
import androidx.compose.ui.platform.LocalGraphicsContext
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

            }

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
}