package com.teamsolply.solply.maps.dto.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CourseSaveRequestDto(
    @SerialName("courseName")
    val courseName: String,
    @SerialName("courseDescription")
    val courseDescription: String,
    @SerialName("places")
    val places: List<CoursePlaceDto>
)

@Serializable
data class CoursePlaceDto(
    @SerialName("placeId")
    val placeId: Long,
    @SerialName("placeOrder")
    val placeOrder: Int
)
