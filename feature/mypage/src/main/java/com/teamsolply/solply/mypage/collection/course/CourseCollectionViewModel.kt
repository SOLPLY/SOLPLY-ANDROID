package com.teamsolply.solply.mypage.collection.course

import androidx.lifecycle.viewModelScope
import com.teamsolply.solply.mypage.repository.MypageRepository
import com.teamsolply.solply.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.toPersistentList
import kotlinx.collections.immutable.toPersistentSet
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CourseCollectionViewModel @Inject constructor(
    private val mypageRepository: MypageRepository
) :
    BaseViewModel<CourseCollectionState, CourseCollectionIntent, CourseCollectionSideEffect>(
        CourseCollectionState()
    ) {
    override fun handleIntent(intent: CourseCollectionIntent) {
        when (intent) {
            is CourseCollectionIntent.Init -> {
                reduce {
                    copy(
                        townId = intent.townId,
                        townName = intent.townName
                    )
                }
                getCourseList(uiState.value.townId)
            }

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
                            val updatedCourses = courses.map {
                                if (it.courseId == intent.courseId) it.copy(isSelected = false) else it
                            }.toPersistentList()
                            copy(
                                selectedCourses = (selectedCourses - intent.courseId).toPersistentSet(),
                                courses = updatedCourses
                            )
                        }
                    } else {
                        reduce {
                            val updatedCourses = courses.map {
                                if (it.courseId == intent.courseId) it.copy(isSelected = true) else it
                            }.toPersistentList()
                            copy(
                                selectedCourses = (selectedCourses + intent.courseId).toPersistentSet(),
                                courses = updatedCourses
                            )
                        }
                    }
                } else {
                    postSideEffect(CourseCollectionSideEffect.NavigateToMap(courseId = intent.courseId))
                }
            }

            is CourseCollectionIntent.DialogConfirmClick -> {
                deleteCourses(selectedCourses = uiState.value.selectedCourses.toList())
            }

            is CourseCollectionIntent.DialogDismissClick -> {
                reduce {
                    copy(dialogState = false)
                }
            }
        }
    }

    private fun getCourseList(townId: Long) {
        viewModelScope.launch {
            mypageRepository.getCourseList(townId).onSuccess {
                reduce {
                    copy(
                        courses = it.toPersistentList()
                    )
                }
            }
        }
    }

    private fun deleteCourses(selectedCourses: List<Long>) {
        viewModelScope.launch {
            mypageRepository.deleteCourses(selectedCourses).onSuccess {
                reduce {
                    copy(
                        selectedCourses = emptySet(),
                        selectMode = false,
                        dialogState = false
                    )
                }
                getCourseList(townId = uiState.value.townId)
            }
        }
    }
}
