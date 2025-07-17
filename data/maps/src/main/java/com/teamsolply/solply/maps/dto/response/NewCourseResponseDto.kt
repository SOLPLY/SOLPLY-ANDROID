package com.teamsolply.solply.maps.dto.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NewCourseResponseDto(
    @SerialName("courseId")
    val courseId: Long,
)
