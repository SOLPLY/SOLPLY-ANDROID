package com.teamsolply.solply.collection.model

import com.teamsolply.solply.model.PlaceType

data class CourseInfoEntity(
    val courseId: Long,
    val courseName: String,
    val placeTypeList: List<PlaceType>,
    val imageUrls: List<String>,
    val isSaved: Boolean,
    val isSelected: Boolean = false
)
