package com.teamsolply.solply.maps.model

import com.teamsolply.solply.model.PlaceType

data class PlaceInfo(
    val placeId: Int,
    val placeName: String,
    val primaryTag: PlaceType,
    val description: String,
    val imageUrls: List<Int>,
    val address: String,
    val priority: Int,
    val latitude: Double,
    val longitude: Double,
    val contactNumber: String,
    val openingHours: String,
    val snsLink: String,
    val isBookmarked: Boolean
)
