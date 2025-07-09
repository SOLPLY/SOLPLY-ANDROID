package com.teamsolply.solply.maps.model

import com.teamsolply.solply.model.PlaceType

data class PlaceInfo(
    val placeId: Int,
    val placeName: String,
    val primaryTag: PlaceType,
    val description: String,
    val imageInfos: List<Int>,
    val address: String,
    val latitude: Double,
    val longitude: Double,
    val contactNumber: String,
    val openingHours: String,
    val snsLink: List<SnsLink>,
    val isBookmarked: Boolean,
    val placeType: String,
    val placeDefaultId: Int
)

data class SnsLink(
    val platform: String,
    val url: String
)
