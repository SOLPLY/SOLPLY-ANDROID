package com.teamsolply.solply.course.model

data class CourseData(
    val courseId: Long,
    val courseName: String,
    val imageUrl: String,
    val tagList: List<String>,
    val isSaved: Boolean
)
