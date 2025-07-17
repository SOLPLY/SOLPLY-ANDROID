package com.teamsolply.solply.mypage.dto.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PlaceTownListResponseDto(
    @SerialName("folderThumbnailList")
    val townList: List<PlaceTownResponseDto>
)

@Serializable
data class PlaceTownResponseDto(
    @SerialName("townId")
    val townId: Long,
    @SerialName("townName")
    val townName: String,
    @SerialName("folderThumbnailUrl")
    val imageUrl: String
)
