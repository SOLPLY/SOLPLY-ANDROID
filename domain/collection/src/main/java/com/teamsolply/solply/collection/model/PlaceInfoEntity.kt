package com.teamsolply.solply.collection.model

import com.teamsolply.solply.model.PlaceType

data class PlaceInfoEntity(
    val placeId: Long,
    val placeName: String,
    val placeType: PlaceType,
    val imageUrls: String,
    val isSaved: Boolean,
    val isSelected: Boolean = false
)
