package com.teamsolply.solply.mypage.collection.course

import com.teamsolply.solply.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CourseCollectionViewModel @Inject constructor() :
    BaseViewModel<CourseCollectionState, CourseCollectionIntent, CourseCollectionSideEffect>(
        CourseCollectionState()
    ) {
    override fun handleIntent(intent: CourseCollectionIntent) {
        TODO("Not yet implemented")
    }
}
