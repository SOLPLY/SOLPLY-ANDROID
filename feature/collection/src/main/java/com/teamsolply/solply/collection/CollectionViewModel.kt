package com.teamsolply.solply.collection

import androidx.lifecycle.viewModelScope
import com.teamsolply.solply.collection.model.MypageTab
import com.teamsolply.solply.collection.repository.CollectionRepository
import com.teamsolply.solply.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import okhttp3.internal.toImmutableList
import javax.inject.Inject

@HiltViewModel
class CollectionViewModel @Inject constructor(
    private val collectionRepository: CollectionRepository
) :
    BaseViewModel<CollectionMenuState, CollectionMenuIntent, CollectionSideEffect>(CollectionMenuState()) {
    override fun handleIntent(intent: CollectionMenuIntent) {
        when (intent) {
            is CollectionMenuIntent.SelectPlaceTown -> {
                postSideEffect(
                    CollectionSideEffect.NavigateToPlaceCollection(
                        townId = intent.townId,
                        townName = intent.townName
                    )
                )
            }

            is CollectionMenuIntent.SelectCourseTown -> {
                postSideEffect(
                    CollectionSideEffect.NavigateToCourseCollection(
                        townId = intent.townId,
                        townName = intent.townName
                    )
                )
            }

            is CollectionMenuIntent.BackButtonClick -> {
                postSideEffect(CollectionSideEffect.NavigateToBack)
            }

            is CollectionMenuIntent.SelectPlaceTab -> {
                reduce {
                    copy(selectedTab = MypageTab.PLACE)
                }
            }

            is CollectionMenuIntent.SelectCourseTab -> {
                reduce {
                    copy(selectedTab = MypageTab.COURSE)
                }
            }

            is CollectionMenuIntent.EmptyButtonClick -> {
                when (intent.mypageTab) {
                    MypageTab.PLACE -> {
                        postSideEffect(CollectionSideEffect.NavigateToPLace)
                    }

                    MypageTab.COURSE -> {
                        postSideEffect(CollectionSideEffect.NavigateToCourse)
                    }
                }
            }
        }
    }

    fun getPlaceTownList() {
        viewModelScope.launch {
            collectionRepository.getPlaceTownList().onSuccess {
                reduce {
                    copy(
                        placeTowns = it.toImmutableList()
                    )
                }
            }
        }
    }

    fun getCourseTownList() {
        viewModelScope.launch {
            collectionRepository.getCourseTownList().onSuccess {
                reduce {
                    copy(
                        courseTowns = it.toImmutableList()
                    )
                }
            }
        }
    }
}
