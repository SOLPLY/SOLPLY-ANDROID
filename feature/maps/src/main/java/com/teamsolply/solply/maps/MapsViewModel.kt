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
        }
    }

    private fun moveCourseItem(fromIndex: Int, toIndex: Int) {
        reduce {
            val newCourseList = uiState.value.course.toMutableList()
            val item = newCourseList.removeAt(fromIndex)
            newCourseList.add(toIndex, item)
            copy(course = newCourseList)
        }
    }

    private fun removeCourseItem(itemToRemove: Int) {
        reduce {
            val currentList = uiState.value.course
            if (currentList.size <= 2) {
                postSideEffect(MapsSideEffect.DisabledRemoveCourse)
                return@reduce this
            }

            val newList = uiState.value.course.toMutableList()
            newList.removeAt(itemToRemove)
            copy(
                course = newList
            )
        }
    }
}