package com.teamsolply.solply.course.favoriteTown.model

import com.teamsolply.solply.ui.base.UiState

data class CourseState(
    val selectedTownId: Long? = null,
    val selectedRegionId: Long? = null,
    val regions: List<Region> = emptyList(),
    val townsByRegion: Map<Long, List<TownLite>> = emptyMap(),
    val townInfo: FavoriteTownInfoEntity? = null
) : UiState
