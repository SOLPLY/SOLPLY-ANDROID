package com.teamsolply.solply.maps

import com.teamsolply.solply.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MapsViewModel @Inject constructor() :
    BaseViewModel<MapsState, MapsIntent, MapsSideEffect>(MapsState()) {
    override fun handleIntent(intent: MapsIntent) {
        when (intent) {
            is MapsIntent.StartCourseMove -> reduce { copy(iconVisibility = intent.iconVisibility) }

            is MapsIntent.MoveCourseItem -> moveCourseItem(
                fromIndex = intent.fromIndex,
                toIndex = intent.toIndex
            )

            is MapsIntent.RemoveCourseItem -> {
                removeCourseItem(
                    itemToRemove = intent.itemToRemove
                )
            }

            is MapsIntent.ReturnToHomeClick -> {
                postSideEffect(MapsSideEffect.NavigateToReturnHome)
            }

            is MapsIntent.BackButtonClick -> {
                postSideEffect(MapsSideEffect.NavigateToBack)
            }
        }
    }

    private fun moveCourseItem(fromIndex: Int, toIndex: Int) {
        reduce {
            val newCourseList = courses.toMutableList()
            val item = newCourseList.removeAt(fromIndex)
            newCourseList.add(toIndex, item)
            copy(courses = newCourseList)
        }
    }

    private fun removeCourseItem(itemToRemove: Int) {
        val currentList = uiState.value.courses
        if (currentList.size <= 2) {
            postSideEffect(MapsSideEffect.DisabledRemoveCourse)
            return
        }
        reduce {
            val newList = courses.toMutableList()
            newList.removeAt(itemToRemove)
            copy(
                courses = newList
            )
        }
    }
}
