package com.teamsolply.solply.place.dto.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GetPlacesResponseDto(
    @SerialName("places")
    val places: List<PlaceDto>
)

@Serializable
data class PlaceDto(
    @SerialName("placeId")
    val placeId: Long,

    @SerialName("placeName")
    val placeName: String,

    @SerialName("thumbnailImageUrl")
    val thumbnailImageUrl: String,

    @SerialName("mainTag")
    val mainTag: String,

    @SerialName("isBookmarked")
    val isBookmarked: Boolean
)
