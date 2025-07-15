package com.teamsolply.solply.mypage.model

import com.teamsolply.solply.model.PlaceType

data class TownEntity(
    val townId: Int,
    val townName: String,
    val courseName: String,
    val tagList: List<PlaceType>,
    val imageUrl: String
)
