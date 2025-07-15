package com.teamsolply.solply.maps.dto.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CoursesResponseDto(
    @SerialName("courses")
    val courses: List<CourseDto>
)

@Serializable
data class CourseDto(
    @SerialName("courseId")
    val courseId: Long,

    @SerialName("title")
    val title: String,

    @SerialName("placeCount")
    val placeCount: Int,

    @SerialName("thumbnailImage")
    val thumbnailImage: String,

    @SerialName("mainTags")
    val mainTags: List<String>,

    @SerialName("isBookmarked")
    val isBookmarked: Boolean,

    @SerialName("isActive")
    val isActive: Boolean
)