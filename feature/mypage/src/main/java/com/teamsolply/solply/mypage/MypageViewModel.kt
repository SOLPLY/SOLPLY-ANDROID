package com.teamsolply.solply.mypage

import com.teamsolply.solply.mypage.model.MypageTab
import com.teamsolply.solply.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MypageViewModel @Inject constructor() :
    BaseViewModel<MypageState, MypageIntent, MypageSideEffect>(MypageState()) {
    override fun handleIntent(intent: MypageIntent) {
        when (intent) {
            is MypageIntent.SelectTown -> {
                // TODO town에 저장된 place or course 리스트 조회 api
                // MypageContract places에 반영하면 되나?
                when (currentState.selectedTab) {
                    MypageTab.PLACE -> {
                        reduce {
                            copy(isPlaceTownSelected = true)
                        }
                        postSideEffect(MypageSideEffect.NavigateToPlaceCollection(town = intent.selectedTown))
                    }

                    MypageTab.COURSE -> {
                        reduce {
                            copy(isCourseTownSelected = true)
                        }
                    }
                }
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
        }
    }
}

