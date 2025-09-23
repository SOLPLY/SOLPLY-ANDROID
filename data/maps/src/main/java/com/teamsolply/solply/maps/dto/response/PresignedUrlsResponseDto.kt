package com.teamsolply.solply.maps.dto.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PresignedUrlsResponseDto(
    @SerialName("presignedUrlInfos")
    val presignedUrlInfos: List<PresignedUrlInfo>
)

@Serializable
data class PresignedUrlInfo(
    @SerialName("originalFileName")
    val originalFileName: String,
    @SerialName("tempFileKey")
    val tempFileKey: String,
    @SerialName("presignedUrl")
    val presignedUrl: String,
    @SerialName("expirationSeconds")
    val expirationSeconds: Int
)