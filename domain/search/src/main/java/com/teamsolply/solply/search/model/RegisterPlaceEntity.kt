package com.teamsolply.solply.search.model

data class RegisterPlaceEntity(
    val placeName: String,
    val address: String,
    val mainTagId: Long,
    val subTagAIds: List<Long>,
    val subTagBIds: List<Long>,
    val reason: String,
    val images: List<PlaceImageEntity>
)

data class PlaceImageEntity(
    val displayOrder: Long,
    val tempFileKey: String
)

data class RegisterPlaceResponseEntity(
    val placeRequestId: Long,
    val userId: Long
)