package com.teamsolply.solply.course

import com.teamsolply.solply.ui.base.SideEffect
import com.teamsolply.solply.ui.base.UiIntent
import com.teamsolply.solply.ui.base.UiState

data class CourseState(
    val g: String = ""
) : UiState

sealed interface CourseIntent : UiIntent

sealed interface CourseSideEffect : SideEffect
