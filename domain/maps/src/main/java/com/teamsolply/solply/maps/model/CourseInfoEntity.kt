package com.teamsolply.solply.maps.model

import com.teamsolply.solply.model.PlaceType

data class CourseInfoEntity(
    val courseId: Long,
    val courseName: String,
    val thumbnailImage: String,
    val mainTags: List<PlaceType>,
    val isBookmarked: Boolean,
    val isDuplicated: Boolean,
    val isPlaceCountLimited: Boolean,
    val isActive: Boolean
)
