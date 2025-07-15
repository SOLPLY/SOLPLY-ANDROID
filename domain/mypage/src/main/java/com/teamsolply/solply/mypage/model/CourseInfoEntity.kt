package com.teamsolply.solply.mypage.model

import com.teamsolply.solply.model.PlaceType

data class CourseInfoEntity(
    val courseId: Int,
    val courseName: String,
    val placeTypeList: List<PlaceType>,
    val imageUrls: List<Int>,
    val isSelected: Boolean = false
)
