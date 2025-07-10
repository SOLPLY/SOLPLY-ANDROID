package com.teamsolply.solply.mypage.model

import com.teamsolply.solply.model.PlaceType

data class PlaceCard(
    val placeId: Int,
    val placeName: String,
    val placeType: PlaceType,
    val imageUrls: List<Int>
)
