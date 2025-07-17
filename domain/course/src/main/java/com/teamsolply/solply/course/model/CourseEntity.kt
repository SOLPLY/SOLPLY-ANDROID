package com.teamsolply.solply.course.model

import com.teamsolply.solply.model.PlaceType

data class CourseEntity(
    val courseId: Long,
    val courseName: String,
    val imageUrl: String,
    val tagList: List<PlaceType>,
    val isSaved: Boolean
)
