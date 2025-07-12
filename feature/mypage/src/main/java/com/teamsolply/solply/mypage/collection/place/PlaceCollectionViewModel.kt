package com.teamsolply.solply.mypage.collection.place

import com.teamsolply.solply.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PlaceCollectionViewModel @Inject constructor() :
    BaseViewModel<PlaceCollectionState, PlaceCollectionIntent, PlaceCollectionSideEffect>(
        PlaceCollectionState()
    ) {
    override fun handleIntent(intent: PlaceCollectionIntent) {
        when (intent) {
            is PlaceCollectionIntent.SelectClick -> {
                reduce {
                    copy(selectMode = true)
                }
            }

            is PlaceCollectionIntent.CancelClick -> {
                reduce {
                    copy(selectMode = false)
                }
            }

            is PlaceCollectionIntent.BackButtonClick -> {
                postSideEffect(PlaceCollectionSideEffect.NavigateToBack)
            }
        }
    }
}
