package com.teamsolply.solply.course.dto.response

import com.teamsolply.solply.model.PlaceType
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RecommendedCourseListResponseDto(
    @SerialName("courses")
    val courseList: List<RecommendedCourseResponseDto>
)

@Serializable
data class RecommendedCourseResponseDto(
    @SerialName("courseId")
    val courseId: Long,

    @SerialName("courseName")
    val courseName: String,

    @SerialName("thumbnailImage")
    val thumbnailImage: String,

    @SerialName("mainTags")
    val tagList: List<PlaceType>,

    @SerialName("isBookmarked")
    val isSaved: Boolean
)
