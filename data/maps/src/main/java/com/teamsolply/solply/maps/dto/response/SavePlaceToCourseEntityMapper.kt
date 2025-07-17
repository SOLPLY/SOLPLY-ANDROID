package com.teamsolply.solply.maps.dto.response

import com.teamsolply.solply.maps.model.AddedPlaceInfoEntity
import com.teamsolply.solply.maps.model.SavePlaceToCourseEntity

fun SavePlaceToCourseResponseDto.toEntity(): SavePlaceToCourseEntity {
    return SavePlaceToCourseEntity(
        courseId = courseId,
        courseName = courseName,
        isNewCourse = isNewCourse,
        addedPlaceInfo = addedPlaceInfo.toEntity()
    )
}

fun AddedPlaceInfoDto.toEntity(): AddedPlaceInfoEntity {
    return AddedPlaceInfoEntity(
        placeId = placeId,
        placeOrder = placeOrder
    )
}
