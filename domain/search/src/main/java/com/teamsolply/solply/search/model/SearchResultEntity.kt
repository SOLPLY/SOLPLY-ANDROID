package com.teamsolply.solply.search.model

import com.teamsolply.solply.model.PlaceType

data class SearchResultEntity(
    val placeId: Long,
    val placeName: String,
    val thumbnailImageUrl: String,
    val primaryTag: PlaceType,
    val address: String,
    val isBookmarked: Boolean
)