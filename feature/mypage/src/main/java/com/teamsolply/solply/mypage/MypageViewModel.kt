package com.teamsolply.solply.mypage

import androidx.lifecycle.viewModelScope
import com.teamsolply.solply.mypage.model.MypageTab
import com.teamsolply.solply.mypage.repository.MypageRepository
import com.teamsolply.solply.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import okhttp3.internal.toImmutableList
import javax.inject.Inject

@HiltViewModel
class MypageViewModel @Inject constructor(
    private val mypageRepository: MypageRepository
) :
    BaseViewModel<MypageState, MypageIntent, MypageSideEffect>(MypageState()) {
    override fun handleIntent(intent: MypageIntent) {
        when (intent) {
            is MypageIntent.SelectPlaceTown -> {
                postSideEffect(
                    MypageSideEffect.NavigateToPlaceCollection(
                        townId = intent.townId,
                        townName = intent.townName
                    )
                )
            }

            is MypageIntent.SelectCourseTown -> {
                postSideEffect(
                    MypageSideEffect.NavigateToCourseCollection(
                        townId = intent.townId,
                        townName = intent.townName
                    )
                )
            }

            is MypageIntent.BackButtonClick -> {
                postSideEffect(MypageSideEffect.NavigateToBack)
            }

            is MypageIntent.SelectPlaceTab -> {
                reduce {
                    copy(selectedTab = MypageTab.PLACE)
                }
            }

            is MypageIntent.SelectCourseTab -> {
                reduce {
                    copy(selectedTab = MypageTab.COURSE)
                }
            }

            is MypageIntent.EmptyButtonClick -> {
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
