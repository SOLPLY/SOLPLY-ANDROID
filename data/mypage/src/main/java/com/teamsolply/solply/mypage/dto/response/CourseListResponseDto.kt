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

    @SerialName("title")
    val title: String,

    @SerialName("thumbnailImageUrl")
    val imageUrl: String,

    @SerialName("mainTags")
    val mainTags: List<PlaceType>,

    @SerialName("isBookmarked")
    val isBookmarked: Boolean
)
