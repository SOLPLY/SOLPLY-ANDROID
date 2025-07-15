package com.teamsolply.solply.maps.model

import com.teamsolply.solply.model.PlaceType

data class CourseInfoEntity(
    val courseId: Int,
    val title: String,
    val placeCount: Int,
    val thumbnailImage: String,
    val mainTag: List<PlaceType>,
    val isBookmarked: Boolean,
    val isActive: Boolean
)
