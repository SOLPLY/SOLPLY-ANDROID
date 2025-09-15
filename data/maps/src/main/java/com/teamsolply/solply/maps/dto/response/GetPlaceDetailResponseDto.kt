package com.teamsolply.solply.maps.dto.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GetPlaceDetailResponseDto(

    @SerialName("placeId")
    val placeId: Long,

    @SerialName("placeName")
    val placeName: String,

    @SerialName("mainTag")
    val mainTag: String,

    @SerialName("introduction")
    val introduction: String,

    @SerialName("imageInfos")
    val imageInfoDtos: List<ImageInfoDto>,

    @SerialName("address")
    val address: String,

    @SerialName("latitude")
    val latitude: String,

    @SerialName("longitude")
    val longitude: String,

    @SerialName("contactNumber")
    val contactNumber: String?,

    @SerialName("openingHours")
    val openingHours: String,

    @SerialName("snsLinks")
    val snsLinkDtos: List<SnsLinkDto>,

    @SerialName("isBookmarked")
    val isBookmarked: Boolean,

    @SerialName("placeType")
    val placeType: String,

    @SerialName("placeDefaultId")
    val placeDefaultId: Long
)

@Serializable
data class ImageInfoDto(
    @SerialName("displayOrder")
    val displayOrder: Int,

    @SerialName("url")
    val url: String
)

@Serializable
data class SnsLinkDto(
    @SerialName("snsPlatform")
    val snsPlatform: String,

    @SerialName("url")
    val url: String
)
