package com.teamsolply.solply.maps.dto.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PresignedUrlsRequestDto(
    @SerialName("files")
    val files: List<FileDto>
)

@Serializable
data class FileDto(
    @SerialName("fileName")
    val fileName: String
)
