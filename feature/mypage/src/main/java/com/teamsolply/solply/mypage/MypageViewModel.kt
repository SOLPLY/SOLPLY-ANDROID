package com.teamsolply.solply.mypage

import com.teamsolply.solply.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MypageViewModel @Inject constructor() :
    BaseViewModel<MypageState, MypageIntent, MypageSideEffect>(MypageState()) {
    override fun handleIntent(intent: MypageIntent) {
        when (intent) {
            is MypageIntent.SelectTown -> reduce {
//                postSideEffect(MypageSideEffect.ShowPlaceOfTown(intent.selectedTown))
                // TODO town에 저장된 place 리스트 조회 api
                // MypageContract places에 반영하면 되나?
                copy(isTownSelected = true)
            }

            is MypageIntent.BackButtonClick -> {
                if (currentState.isTownSelected) {
                    reduce {
                        copy(isTownSelected = false)
                    }
                } else {
                    postSideEffect(MypageSideEffect.NavigateToBack)
                }
            }
        }
    }
}
