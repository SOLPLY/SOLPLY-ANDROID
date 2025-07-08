package com.teamsolply.solply.maps.model

import com.teamsolply.solply.model.PlaceType

data class PlaceInfo(
    val placeId: Int,
    val placeName: String,
    val placeTag: PlaceType,
    val placeAddress: String,
    val placeImageRes: Int,
    val priority: Int,
    val latitude: Double,
    val longitude: Double
)
