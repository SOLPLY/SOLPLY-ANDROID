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
    val courseId: Int,

    @SerialName("title")
    val courseName: String,

    @SerialName("thumbnailImage")
    val imageUrl: String,

    @SerialName("mainTags")
    val tagList: List<PlaceType>,

    @SerialName("isBookmarked")
    val isSaved: Boolean
)