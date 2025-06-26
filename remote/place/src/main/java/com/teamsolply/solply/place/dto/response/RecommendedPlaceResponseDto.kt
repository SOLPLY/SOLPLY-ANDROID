package com.teamsolply.solply.place.dto.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RecommendedPlaceResponseDto(
    @SerialName("placeName")
    val placeName: String
)
