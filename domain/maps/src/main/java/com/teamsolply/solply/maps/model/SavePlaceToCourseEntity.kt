package com.teamsolply.solply.maps.model

data class SavePlaceToCourseEntity(
    val courseId: Long,
    val courseName: String,
    val isNewCourse: Boolean,
    val addedPlaceInfo: AddedPlaceInfoEntity
)

data class AddedPlaceInfoEntity(
    val placeId: Long,
    val placeOrder: Long
)