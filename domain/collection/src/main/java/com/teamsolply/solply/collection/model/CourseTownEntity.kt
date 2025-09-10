package com.teamsolply.solply.collection.model

import com.teamsolply.solply.model.PlaceType

data class CourseTownEntity(
    val townId: Long,
    val townName: String,
    val courseName: String,
    val tagList: List<PlaceType>,
    val imageUrl: String
)
