package com.teamsolply.solply.maps.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SaveCourseRequestDto(
    @SerialName("courseName")
    val courseName: String
)
