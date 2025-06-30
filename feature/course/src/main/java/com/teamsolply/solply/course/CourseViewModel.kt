package com.teamsolply.solply.course

import com.teamsolply.solply.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CourseViewModel @Inject constructor(
) : BaseViewModel<CourseState, CourseIntent, CourseSideEffect>(CourseState()) {
    override fun handleIntent(intent: CourseIntent) {
    }
}