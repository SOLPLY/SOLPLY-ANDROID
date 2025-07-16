package com.teamsolply.solply.place

import androidx.lifecycle.viewModelScope
import com.teamsolply.solply.model.PlaceType
import com.teamsolply.solply.place.model.PlaceData
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
            fetchMainTags()
        }
    }

    override fun handleIntent(intent: PlaceIntent) {
        when (intent) {
            is PlaceIntent.LoadUserInfo -> fetchUserInfo()
            is PlaceIntent.LoadPlaces -> fetchRecommendPlace(intent.townId)
            is PlaceIntent.ChangeSelectedMainFilter -> {
                reduce {
                    copy(
                        selectedMainTagId = intent.mainFilterId,
                        selectedMainFilter = intent.mainFilterName,
                        selectedOptionFilter = persistentListOf()
                    )
                }
                fetchSubTags()
            }

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
                    reduce { copy(recommendPlaces = placesList.toPersistentList()) }
                }
        }
    }

    private fun fetchMainTags() {
        viewModelScope.launch {
            repository.getMainTags()
                .onSuccess { tagList ->
                    reduce {
                        copy(
                            selectedMainTagId = tagList[0].tagId,
                            mainFilterItems = tagList.toPersistentList()
                        )
                    }
                    // TODO 전체 선택할 때 404 예외 처리
                }
        }
    }

    private fun fetchSubTags() {
        viewModelScope.launch {
            repository.getSubTags(uiState.value.selectedMainTagId)
                .onSuccess { tagList ->
                    reduce {
                        copy(
                            subFilterItems = tagList.toPersistentList()
                        )
                    }
                }
        }
    }

    private fun fetchUserInfo() {
        viewModelScope.launch {
            repository.getUserInfo()
                .onSuccess { userInfo ->
                    reduce { copy(userInfo = userInfo) }

                    repository.getPlaces(
                        townId = userInfo.selectedTown.townId,
                        mainTagId = null,
                        subTagAIdList = emptyList(),
                        subTagBIdList = emptyList()
                    ).onSuccess {
                        reduce {
                            copy(
                                placeList = it.map {
                                    PlaceData(
                                        placeId = it.placeId,
                                        placeName = it.placeName,
                                        thumbnailUrl = it.thumbnailImageUrl,
                                        primaryTag = PlaceType.valueOf(it.primaryTag),
                                        isBookmarked = it.isBookmarked
                                    )
                                }.toPersistentList()
                            )
                        }
                    }
                }
        }
    }
}
