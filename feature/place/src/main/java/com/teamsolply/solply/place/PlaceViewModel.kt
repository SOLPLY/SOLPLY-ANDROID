package com.teamsolply.solply.place

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.teamsolply.solply.place.model.SaveAutoSignInEntity
import com.teamsolply.solply.place.repository.PlaceRepository
import com.teamsolply.solply.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PlaceViewModel @Inject constructor(
    private val repository: PlaceRepository
) : BaseViewModel<PlaceState, PlaceIntent, PlaceSideEffect>(PlaceState()) {

    init {
        viewModelScope.launch {
            repository.saveAutoSignIn(autoSignIn = SaveAutoSignInEntity(autoSignIn = true))
            sendIntent(PlaceIntent.LoadUserInfo)
            sendIntent(PlaceIntent.LoadPlaces(townId = uiState.value.townId))
            sendIntent(PlaceIntent.LoadMainTags)
            sendIntent(PlaceIntent.LoadSubTags(parentId = uiState.value.selectedMainTagId))
        }
    }

    override fun handleIntent(intent: PlaceIntent) {
        when (intent) {
            is PlaceIntent.LoadUserInfo -> fetchUserInfo()
            is PlaceIntent.LoadPlaces -> fetchRecommendPlace(intent.townId)
            is PlaceIntent.LoadMainTags -> fetchMainTags()
            is PlaceIntent.ChangeSelectedMainFilter -> {
                reduce {
                    copy(
                        selectedMainTagId = intent.mainFilterId,
                        selectedMainFilter = intent.mainFilterName,
                        selectedOptionFilter = persistentListOf()
                    )
                }
                sendIntent(PlaceIntent.LoadSubTags(parentId = intent.mainFilterId))
            }

            is PlaceIntent.LoadSubTags -> fetchSubTags(intent.parentId)
            is PlaceIntent.PlaceClicked -> postSideEffect(PlaceSideEffect.NavigateToMap(intent.placeId))
            PlaceIntent.Retry -> {}

            is PlaceIntent.SelectOptionFilter -> {
                val currentOptionFilter = intent.optionTagId
                val updatedOptionFilter =
                    if (uiState.value.selectedOptionFilter.contains(currentOptionFilter)) {
                        uiState.value.selectedOptionFilter - currentOptionFilter
                    } else {
                        uiState.value.selectedOptionFilter + currentOptionFilter
                    }
                reduce {
                    copy(
                        selectedOptionFilter = updatedOptionFilter.toPersistentList()
                    )
                }
            }

            // 메인 필터 바텀시트 visible
            PlaceIntent.ChangeMainFilterBottomSheetVisible -> reduce {
                copy(isMainFilterBottomSheetVisible = !isMainFilterBottomSheetVisible)
            }

            // 메인 필터 변경
            is PlaceIntent.ChangeSelectedMainFilter -> reduce {
                copy(selectedMainFilter = intent.mainFilterName)
            }

            // 옵션 필터 바텀시트 visible
            PlaceIntent.ChangeOptionFilterBottomSheetVisible -> reduce {
                copy(isOptionFilterBottomSheetVisible = !isOptionFilterBottomSheetVisible)
            }

            // 옵션 필터 변경
            is PlaceIntent.ChangeSelectedOptionFilter -> reduce {
                val updatedList = selectedOptionFilter.toMutableList().apply {
                    if (contains(intent.optionFilterId)) {
                        remove(intent.optionFilterId)
                    } else {
                        add(
                            intent.optionFilterId
                        )
                    }
                }

                copy(
                    selectedOptionFilter = updatedList.toPersistentList()
                )
            }

            // 옵션 필터 초기화
            PlaceIntent.ClearOptionFilter -> reduce {
                copy(selectedOptionFilter = persistentListOf())
            }
        }
    }

    private fun fetchRecommendPlace(townId: Long) {
        viewModelScope.launch {
            repository.getRecommendedPlace(townId)
                .onSuccess { placesList ->
                    reduce { copy(recommendPlaces = placesList) }
                }
        }
    }

//    fun onMainTypeSelected(type: String) {
//        val tags = when (type) {
//            "WALK", "BOOK" -> emptyList()
//            else -> listOf(
//                OptionTag(9, "OPTION1", "커피/디저트", 1),
//                OptionTag(10, "OPTION2", "시그니쳐메뉴", 1)
//            )
//        }
//        reduce { copy(optionTags = tags) }
//    }

    private fun fetchMainTags() {
        viewModelScope.launch {
            repository.getMainTags()
                .onSuccess { tagList ->
                    reduce { copy(mainFilterItems = tagList) }
                }
        }
    }

    private fun fetchSubTags(parentId: Int) {
        viewModelScope.launch {
            repository.getSubTags(parentId)
                .onSuccess { tagList ->
                    Log.d("tagList", tagList.toString())
                    reduce { copy(subFilterItems = tagList) }
                }


        }
    }

    private fun fetchUserInfo() {
        viewModelScope.launch {
            repository.getUserInfo()
                .onSuccess { userInfo ->
                    reduce { copy(userInfo = userInfo) }
                }
        }
    }
}
