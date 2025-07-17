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
        }
    }

    override fun handleIntent(intent: PlaceIntent) {
        when (intent) {
            PlaceIntent.Retry -> fetchInitInfo()

            // 메인 필터
            PlaceIntent.MainFilterChipClick -> reduce {
                copy(
                    isMainFilterBottomSheetVisible = true
                )
            }

            PlaceIntent.MainFilterBottomSheetDismiss -> reduce {
                copy(
                    isMainFilterBottomSheetVisible = false
                )
            }

            is PlaceIntent.MainFilterClick -> {
                reduce {
                    copy(
                        selectedMainFilterId = intent.mainFilterId,
                        selectedMainFilter = intent.mainFilterName,
                        selectedOptionAFilter = persistentListOf(),
                        selectedOptionBFilter = persistentListOf()
                    )
                }
                fetchPlaces(
                    townId = uiState.value.userInfo.selectedTown.townId,
                    mainTagId = uiState.value.selectedMainFilterId
                )
                fetchSubTags()
                reduce {
                    copy(
                        isMainFilterBottomSheetVisible = false
                    )
                }
            }

            is PlaceIntent.PlaceClicked -> postSideEffect(PlaceSideEffect.NavigateToMap(intent.placeId))

            // 서브 필터 인텐트
            PlaceIntent.SubFilterChipClick -> reduce {
                copy(isOptionFilterBottomSheetVisible = true)
            }

            is PlaceIntent.SubFilterClick -> {
                when (intent.selectedTagType) {
                    "OPTION1" -> {
                        reduce {
                            val updatedAList = selectedOptionAFilter.toMutableList().apply {
                                if (contains(intent.optionFilterId)) {
                                    remove(intent.optionFilterId)
                                } else {
                                    add(
                                        intent.optionFilterId
                                    )
                                }
                            }
                            copy(
                                selectedOptionAFilter = updatedAList.toPersistentList()
                            )
                        }
                    }

                    "OPTION2" -> {
                        reduce {
                            val updatedBList = selectedOptionBFilter.toMutableList().apply {
                                if (contains(intent.optionFilterId)) {
                                    remove(intent.optionFilterId)
                                } else {
                                    add(intent.optionFilterId)
                                }
                            }
                            copy(
                                selectedOptionBFilter = updatedBList.toPersistentList()
                            )
                        }
                    }
                }
            }

            is PlaceIntent.SubFilterBottomSheetCompleteButtonClick -> {
                fetchPlaces(
                    townId = uiState.value.userInfo.selectedTown.townId,
                    mainTagId = uiState.value.selectedMainFilterId,
                    subTagAIdList = uiState.value.selectedOptionAFilter,
                    subTagBIdList = uiState.value.selectedOptionBFilter
                )
                reduce {
                    copy(
                        isOptionFilterBottomSheetVisible = false
                    )
                }
            }

            is PlaceIntent.SubFilterBottomSheetResetButtonClick -> reduce {
                copy(
                    selectedOptionAFilter = persistentListOf(),
                    selectedOptionBFilter = persistentListOf()
                )
            }

            PlaceIntent.SubFilterBottomSheetDismiss -> reduce {
                copy(
                    selectedOptionAFilter = persistentListOf(),
                    selectedOptionBFilter = persistentListOf(),
                    isOptionFilterBottomSheetVisible = false
                )
            }
        }
    }

    private fun fetchInitInfo() {
        viewModelScope.launch {
            repository.getUserInfo()
                .onSuccess { userInfo ->
                    reduce { copy(userInfo = userInfo) }

                    fetchPlaces(
                        townId = userInfo.selectedTown.townId,
                        mainTagId = null,
                        subTagAIdList = null,
                        subTagBIdList = null
                    )
                    fetchRecommendPlace(
                        townId = userInfo.selectedTown.townId
                    )
                    fetchMainTags()
                    reduce {
                        copy(
                            selectedMainFilter = "ALL",
                            mainFilterItems = persistentListOf(),
                            subFilterItems = persistentListOf()
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
                            selectedMainFilterId = 0,
                            mainFilterItems = tagList.toPersistentList()
                        )
                    }
                    // TODO 전체 선택할 때 404 예외 처리
                }
        }
    }

    private fun fetchSubTags() {
        if (uiState.value.selectedMainFilterId != 0) {
            viewModelScope.launch {
                repository.getSubTags(uiState.value.selectedMainFilterId)
                    .onSuccess { tagList ->
                        reduce {
                            copy(
                                subFilterItems = tagList.toPersistentList()
                            )
                        }
                    }
            }
        } else {
            reduce { copy(subFilterItems = persistentListOf()) }
        }
    }

    private fun fetchPlaces(
        townId: Long,
        mainTagId: Int? = null,
        subTagAIdList: List<Int>? = null,
        subTagBIdList: List<Int>? = null
    ) {
        viewModelScope.launch {
            repository.getPlaces(
                townId = townId,
                mainTagId = if (mainTagId == 0) null else mainTagId,
                subTagAIdList = subTagAIdList,
                subTagBIdList = subTagBIdList
            ).onSuccess { placeEntities ->
                reduce {
                    copy(
                        placeList = placeEntities.map {
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
