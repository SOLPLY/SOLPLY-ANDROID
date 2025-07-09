package com.teamsolply.solply.maps.model

import com.teamsolply.solply.model.PlaceType

data class CourseInfo(
    val courseId: Int,
    val courseName: String,
    val placeCount: Int,
    val thumbnailImage: Int,
    val mainTag: List<PlaceType>,
    val isBookmarked: Boolean,
    val isActive: Boolean
)
