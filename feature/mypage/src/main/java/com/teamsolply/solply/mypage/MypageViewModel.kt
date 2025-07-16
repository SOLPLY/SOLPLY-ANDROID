package com.teamsolply.solply.mypage

import androidx.lifecycle.viewModelScope
import com.teamsolply.solply.mypage.model.MypageTab
import com.teamsolply.solply.mypage.repository.MypageRepository
import com.teamsolply.solply.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MypageViewModel @Inject constructor(
    private val mypageRepository: MypageRepository
) :
    BaseViewModel<MypageState, MypageIntent, MypageSideEffect>(MypageState()) {
    override fun handleIntent(intent: MypageIntent) {
        when (intent) {
            is MypageIntent.SelectPlaceTown -> {
                // TODO town에 저장된 place or course 리스트 조회 api
                // MypageContract places에 반영하면 되나?
                reduce {
                    copy(isPlaceTownSelected = true)
                }
                postSideEffect(
                    MypageSideEffect.NavigateToPlaceCollection(
                        townId = intent.townId,
                        townName = intent.townName
                    )
                )
            }

            is MypageIntent.SelectCourseTown -> {
                reduce {
                    copy(isCourseTownSelected = true)
                }
                postSideEffect(
                    MypageSideEffect.NavigateToCourseCollection(
                        townId = intent.townId,
                        townName = intent.townName
                    )
                )
            }

            is MypageIntent.BackButtonClick -> {
                when (currentState.selectedTab) {
                    MypageTab.PLACE -> {
                        if (currentState.isPlaceTownSelected) {
                            reduce {
                                copy(isPlaceTownSelected = false)
                            }
                        } else {
                            postSideEffect(MypageSideEffect.NavigateToBack)
                        }
                    }

                    MypageTab.COURSE -> {
                        if (currentState.isCourseTownSelected) {
                            reduce {
                                copy(isCourseTownSelected = false)
                            }
                        } else {
                            postSideEffect(MypageSideEffect.NavigateToBack)
                        }
                    }
                }
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
                        placeTowns = it
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
                        courseTowns = it
                    )
                }
            }
        }
    }
}
