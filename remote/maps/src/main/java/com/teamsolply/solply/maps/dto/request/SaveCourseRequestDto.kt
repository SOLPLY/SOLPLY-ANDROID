package com.teamsolply.solply.maps.dto.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SaveCourseRequestDto(
    @SerialName("courseName")
    val courseName: String
)
