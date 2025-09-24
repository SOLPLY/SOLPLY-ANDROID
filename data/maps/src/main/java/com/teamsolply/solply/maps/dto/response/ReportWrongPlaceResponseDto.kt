package com.teamsolply.solply.maps.dto.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ReportWrongPlaceResponseDto(
    @SerialName("reportId")
    val reportId: Long,
    @SerialName("status")
    val status: String
)
