package com.teamsolply.solply.maps.dto.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ReportWrongPlaceRequestDto(
    @SerialName("reportType")
    val reportType: String,
    @SerialName("content")
    val content: String,
    @SerialName("imageKeys")
    val imageKeys: List<String>
)