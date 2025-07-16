package com.teamsolply.solply.place.model

data class PlaceEntity(
    val placeId: Long,
    val placeName: String,
    val thumbnailImageUrl: String,
    val primaryTag: String,
    val isBookmarked: Boolean
)
