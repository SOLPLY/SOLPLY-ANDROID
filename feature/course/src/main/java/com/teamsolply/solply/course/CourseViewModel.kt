package com.teamsolply.solply.course

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.teamsolply.solply.course.repository.CourseRepository
import com.teamsolply.solply.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CourseViewModel @Inject constructor(
    private val courseRepository: CourseRepository
) :
    BaseViewModel<CourseState, CourseIntent, CourseSideEffect>(CourseState()) {
    override fun handleIntent(intent: CourseIntent) {
        when (intent) {
            is CourseIntent.Init -> {
                initLoad()
            }

            is CourseIntent.CourseCardClick -> {
                postSideEffect(CourseSideEffect.NavigateToCourseMap(intent.courseId))
            }

            is CourseIntent.LoadSuccess -> {
//                reduce {
//                    copy(
//                        user = intent.user,
//                        courseList = intent.courses
//                    )
//                }
            }

            is CourseIntent.LoadFailed -> {
                reduce {
                    copy(
                        errorMessage = intent.message
                    )
                }
            }

            is CourseIntent.ChangeFavoriteDialogVisibility -> {
                if (uiState.value.user.selectedTown.townId != intent.selectedTownId) {
                    intent.selectedTownId?.let { id ->
                        viewModelScope.launch {
                            getUserInfo()
                            getCourseList(id)
                        }
                    }
                }
                reduce { copy(isFavoriteDialogVisible = intent.visible) }
            }

            is CourseIntent.ChangeSearchDialogVisibility -> reduce {
                copy(isSearchDialogVisible = intent.visible)
            }

            is CourseIntent.PlaceClicked -> {
                postSideEffect(
                    CourseSideEffect.NavigateToPlaceDetail(
                        placeId = intent.placeId,
                        townId = intent.townId
                    )
                )
            }
        }
    }

    private fun initLoad() {
        viewModelScope.launch {
            getUserInfo()
            getCourseList(uiState.value.user.selectedTown.townId)
        }
    }

    private suspend fun getUserInfo() {
        courseRepository.getUserInfo().onSuccess {
            reduce {
                copy(
                    user = it
                )
            }
        }
    }

    private suspend fun getCourseList(townId: Long) {
        courseRepository.getRecommendedCourse(townId).onSuccess {
            reduce {
                copy(
                    courseList = it.toPersistentList()
                )
            }
        }
    }
}
