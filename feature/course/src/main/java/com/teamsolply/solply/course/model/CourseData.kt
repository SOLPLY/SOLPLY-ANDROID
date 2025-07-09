package com.teamsolply.solply.course.model

data class CourseData(
    val courseId: Long,
    val title: String,
    val thumbnailImage: String,
    val mainTags: List<String>,
    val isBookmarked: Boolean
)
