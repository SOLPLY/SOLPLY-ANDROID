package com.teamsolply.solply.maps.dto.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SavePlaceToCourseResponseDto(
    @SerialName("courseId")
    val courseId: Long,

    @SerialName("courseName")
    val courseName: String,

    @SerialName("isNewCourse")
    val isNewCourse: Boolean,

    @SerialName("addedPlaceInfo")
    val addedPlaceInfo: AddedPlaceInfoDto
)

@Serializable
data class AddedPlaceInfoDto(
    @SerialName("placeId")
    val placeId: Long,

    @SerialName("placeOrder")
    val placeOrder: Long
)
