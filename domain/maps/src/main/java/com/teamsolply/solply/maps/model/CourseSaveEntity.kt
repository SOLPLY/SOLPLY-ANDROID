package com.teamsolply.solply.maps.model

data class CourseSaveEntity(
    val name: String,
    val description: String,
    val places: List<CoursePlace>
)

data class CoursePlace(
    val placeId: Long,
    val order: Int
)