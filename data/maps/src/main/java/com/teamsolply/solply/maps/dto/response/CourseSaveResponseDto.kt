package com.teamsolply.solply.maps.dto.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CourseSaveResponseDto(
    @SerialName("courseId")
    val courseId: Long,
    @SerialName("isNewCourse")
    val isNewCourse: Boolean
)