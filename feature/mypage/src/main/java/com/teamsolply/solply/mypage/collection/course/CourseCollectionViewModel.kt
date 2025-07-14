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
        when (intent) {
            is CourseCollectionIntent.SelectButtonClick -> {
                reduce {
                    copy(selectMode = true)
                }
            }

            is CourseCollectionIntent.DeleteButtonClick -> {
                if (uiState.value.selectedCourses.isNotEmpty()) {
                    reduce {
                        copy(dialogState = true)
                    }
                }
            }

            is CourseCollectionIntent.CancelButtonClick -> {
                reduce {
                    val updatedPlaces = courses.map {
                        if (it.isSelected) it.copy(isSelected = false) else it
                    }
                    copy(
                        courses = updatedPlaces,
                        selectMode = false
                    )
                }
            }

            is CourseCollectionIntent.BackButtonClick ->
                postSideEffect(CourseCollectionSideEffect.NavigateToBack)

            is CourseCollectionIntent.CourseCardClick -> {
                if (uiState.value.selectMode) {
                    if (uiState.value.selectedCourses.contains(intent.courseId)) {
                        reduce {
                            val updatedCourses = courses.toMutableList()
                            val oldCourses = updatedCourses[intent.index]
                            updatedCourses[intent.index] = oldCourses.copy(isSelected = false)
                            copy(
                                selectedCourses = selectedCourses - intent.courseId,
                                courses = updatedCourses
                            )
                        }
                    } else {
                        reduce {
                            val updatedCourses = courses.toMutableList()
                            val oldCourses = updatedCourses[intent.index]
                            updatedCourses[intent.index] = oldCourses.copy(isSelected = true)
                            copy(
                                selectedCourses = selectedCourses + intent.courseId,
                                courses = updatedCourses
                            )
                        }
                    }
                } else {
                    postSideEffect(CourseCollectionSideEffect.NavigateToMap)
                }
            }

            is CourseCollectionIntent.DialogConfirmClick -> {
                reduce {
                    copy(dialogState = false)
                }
                // TODO 삭제 api 요청

                // TODO 삭제 api 응답 후
                reduce {
                    copy(selectMode = false)
                }
                postSideEffect(CourseCollectionSideEffect.DeleteCourses)
            }

            is CourseCollectionIntent.DialogDismissClick -> {
                reduce {
                    copy(dialogState = false)
                }
            }
        }
    }
}
