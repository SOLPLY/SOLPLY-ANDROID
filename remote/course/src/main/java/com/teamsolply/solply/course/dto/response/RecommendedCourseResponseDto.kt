package com.teamsolply.solply.course.dto.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RecommendedCourseResponseDto(
    @SerialName("asd")
    val courseName: String
)
