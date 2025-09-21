package com.teamsolply.solply.maps.dto.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CourseSaveResponseDto(
    @SerialName("updatedCourseId")
    val updatedCourseId: Long,
    @SerialName("updatedCourseName")
    val updatedCourseName: String,
    @SerialName("isNewCourse")
    val isNewCourse: Boolean
)
