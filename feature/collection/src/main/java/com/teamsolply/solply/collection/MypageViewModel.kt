package com.teamsolply.solply.collection

import androidx.lifecycle.viewModelScope
import com.teamsolply.solply.collection.model.MypageTab
import com.teamsolply.solply.collection.repository.MypageRepository
import com.teamsolply.solply.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import okhttp3.internal.toImmutableList
import javax.inject.Inject

@HiltViewModel
class MypageViewModel @Inject constructor(
    private val mypageRepository: MypageRepository
) :
    BaseViewModel<CollectionMenuState, CollectionMenuIntent, MypageSideEffect>(CollectionMenuState()) {
    override fun handleIntent(intent: CollectionMenuIntent) {
        when (intent) {
            is CollectionMenuIntent.SelectPlaceTown -> {
                postSideEffect(
                    MypageSideEffect.NavigateToPlaceCollection(
                        townId = intent.townId,
                        townName = intent.townName
                    )
                )
            }

            is CollectionMenuIntent.SelectCourseTown -> {
                postSideEffect(
                    MypageSideEffect.NavigateToCourseCollection(
                        townId = intent.townId,
                        townName = intent.townName
                    )
                )
            }

            is CollectionMenuIntent.BackButtonClick -> {
                postSideEffect(MypageSideEffect.NavigateToBack)
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
                        postSideEffect(MypageSideEffect.NavigateToPLace)
                    }

                    MypageTab.COURSE -> {
                        postSideEffect(MypageSideEffect.NavigateToCourse)
                    }
                }
            }
        }
    }

    fun getPlaceTownList() {
        viewModelScope.launch {
            mypageRepository.getPlaceTownList().onSuccess {
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
            mypageRepository.getCourseTownList().onSuccess {
                reduce {
                    copy(
                        courseTowns = it.toImmutableList()
                    )
                }
            }
        }
    }
}
