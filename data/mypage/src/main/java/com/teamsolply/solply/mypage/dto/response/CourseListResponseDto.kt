package com.teamsolply.solply.mypage.dto.response

import com.teamsolply.solply.model.PlaceType
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CourseListResponseDto(

    @SerialName("courses")
    val courseList: List<CourseResponseDto>
)

@Serializable
data class CourseResponseDto(
    @SerialName("courseId")
    val courseId: Long,

    @SerialName("courseName")
    val title: String,

    @SerialName("thumbnailImage")
    val imageUrl: String,

    @SerialName("mainTags")
    val mainTags: List<PlaceType>,

    @SerialName("isBookmarked")
    val isSaved: Boolean
)
