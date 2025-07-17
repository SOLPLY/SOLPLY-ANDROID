package com.teamsolply.solply.mypage.model

import com.teamsolply.solply.model.PlaceType

data class PlaceInfoEntity(
    val placeId: Long,
    val placeName: String,
    val placeType: PlaceType,
    val imageUrls: String,
    val isSelected: Boolean = false
)
