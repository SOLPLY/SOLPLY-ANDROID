package com.teamsolply.solply.mypage.dto.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

data class CourseTownListResponseDto(
    @SerialName("folderThumbnailList")
    val townList: List<CourseTownResponseDto>
)

@Serializable
data class CourseTownResponseDto(
    @SerialName("townId")
    val townId: Int,
    @SerialName("townName")
    val townName: String,
    @SerialName("folderThumbnailUrl")
    val imageUrl: String
)