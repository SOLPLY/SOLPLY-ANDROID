package com.teamsolply.solply.place.model

import com.teamsolply.solply.model.PlaceType

data class PlaceInfo(
    val placeId: Int,
    val placeName: String,
    val thumbnailImageUrl: Int,
    val primaryTag: PlaceType,
    val description: String
)