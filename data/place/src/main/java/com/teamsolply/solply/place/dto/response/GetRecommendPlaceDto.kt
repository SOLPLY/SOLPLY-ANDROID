package com.teamsolply.solply.place.dto.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GetRecommendPlaceListDto(
    @SerialName("placeInfos")
    val placeInfos: List<GetRecommendPlaceDto>
)

@Serializable
data class GetRecommendPlaceDto(
    @SerialName("placeId")
    val placeId: Long,
    @SerialName("placeName")
    val placeName: String,
    @SerialName("thumbnailImageUrl")
    val thumbnailImageUrl: String,
    @SerialName("primaryTag")
    val primaryTag: String,
    @SerialName("introduction")
    val introduction: String
)
