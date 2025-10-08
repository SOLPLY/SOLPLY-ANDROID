package com.teamsolply.solply.search.dto.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RegisterPlaceRequestDto(
    @SerialName("placeName")
    val placeName: String,
    @SerialName("address")
    val address: String,
    @SerialName("mainTagId")
    val mainTagId: Long,
    @SerialName("subTagAIds")
    val subTagAIds: List<Long>,
    @SerialName("subTagBIds")
    val subTagBIds: List<Long>,
    @SerialName("reason")
    val reason: String,
    @SerialName("images")
    val images: List<PlaceImageDto>
)

@Serializable
data class PlaceImageDto(
    @SerialName("displayOrder")
    val displayOrder: Long,
    @SerialName("tempFileKey")
    val tempFileKey: String
)