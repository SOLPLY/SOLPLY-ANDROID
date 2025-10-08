package com.teamsolply.solply.search.dto.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RegisterPlaceResponseDto(
    @SerialName("placeRequestId")
    val placeRequestId: Long,
    @SerialName("userId")
    val userId: Long
)