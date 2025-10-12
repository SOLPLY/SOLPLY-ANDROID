package com.teamsolply.solply.mypage.model

import com.teamsolply.solply.model.PlaceType

data class PlaceInfoEntity(
    val placeId: Long,
    val placeName: String,
    val placeType: PlaceType,
    val townId: Long,
    val imageUrls: String,
    val isSaved: Boolean,
    val isSelected: Boolean = false
)
