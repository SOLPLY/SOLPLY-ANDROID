package com.teamsolply.solply.place.model

import com.teamsolply.solply.model.PlaceType

data class RecommendPlaceInfo(
    val placeId: Long,
    val placeName: String,
    val thumbnailImageUrl: String,
    val primaryTag: PlaceType,
    val introduction: String
)