package com.teamsolply.solply.place.model

import com.teamsolply.solply.model.PlaceType

data class PlaceData(
    val placeId: Long,
    val placeName: String,
    val thumbnailUrl: Int,
    val primaryTag: PlaceType,
    val isBookmarked: Boolean
)
