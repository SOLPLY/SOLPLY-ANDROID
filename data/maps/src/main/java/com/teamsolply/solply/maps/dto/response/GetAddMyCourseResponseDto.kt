package com.teamsolply.solply.maps.dto.response

import com.teamsolply.solply.model.PlaceType
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

    @SerialName("courseName")
    val courseName: String,

    @SerialName("thumbnailImage")
    val thumbnailImage: String,

    @SerialName("mainTags")
    val mainTags: List<PlaceType>,

    @SerialName("isBookmarked")
    val isBookmarked: Boolean,

    @SerialName("isDuplicated")
    val isDuplicated: Boolean,

    @SerialName("isPlaceCountLimited")
    val isPlaceCountLimited: Boolean,

    @SerialName("isActive")
    val isActive: Boolean
)
