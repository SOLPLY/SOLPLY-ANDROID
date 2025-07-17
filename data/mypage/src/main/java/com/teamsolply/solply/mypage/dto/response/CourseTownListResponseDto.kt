package com.teamsolply.solply.mypage.dto.response

import com.teamsolply.solply.model.PlaceType
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CourseTownListResponseDto(
    @SerialName("folders")
    val townList: List<CourseTownResponseDto>
)

@Serializable
data class CourseTownResponseDto(
    @SerialName("townId")
    val townId: Long,
    @SerialName("townName")
    val townName: String,
    @SerialName("courseName")
    val courseName: String,
    @SerialName("primaryTags")
    val tagList: List<PlaceType>,
    @SerialName("thumbnailUrl")
    val imageUrl: String
)
