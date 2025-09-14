package com.teamsolply.solply.maps.model

import com.teamsolply.solply.model.PlaceType

data class PlaceDetailEntity(
    val placeId: Long,
    val placeName: String,
    val mainTag: PlaceType,
    val introduction: String,
    val imageInfos: List<ImageInfo>,
    val address: String,
    val latitude: Double,
    val longitude: Double,
    val contactNumber: String,
    val openingHours: String,
    val snsLinks: List<SnsLink>,
    val isBookmarked: Boolean,
    val placeType: String,
    val placeDefaultId: Long
)

data class ImageInfo(
    val displayOrder: Int,
    val url: String
)

data class SnsLink(
    val snsPlatform: String,
    val url: String
)
