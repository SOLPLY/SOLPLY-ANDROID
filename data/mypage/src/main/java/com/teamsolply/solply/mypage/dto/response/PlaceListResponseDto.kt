package com.teamsolply.solply.mypage.dto.response

import com.teamsolply.solply.model.PlaceType
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PlaceListResponseDto(

    @SerialName("places")
    val placeList: List<PlaceResponseDto>
)

@Serializable
data class PlaceResponseDto(
    @SerialName("placeId")
    val placeId: Int,

    @SerialName("placeName")
    val placeName: String,

    @SerialName("thumbnailImageUrl")
    val imageUrl: String,

    @SerialName("primaryTag")
    val tag: PlaceType,

    @SerialName("isBookmarked")
    val isSaved: Boolean
)
