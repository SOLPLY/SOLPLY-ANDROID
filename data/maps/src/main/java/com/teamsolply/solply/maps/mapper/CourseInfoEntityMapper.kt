package com.teamsolply.solply.maps.mapper

import com.teamsolply.solply.maps.dto.response.CourseDto
import com.teamsolply.solply.maps.model.CourseInfoEntity

fun List<CourseDto>.toCourseInfoEntityList(): List<CourseInfoEntity> {
    return this.map { dto ->
        CourseInfoEntity(
            courseId = dto.courseId,
            courseName = dto.courseName,
            thumbnailImage = dto.thumbnailImage,
            mainTags = dto.mainTags,
            isBookmarked = dto.isBookmarked,
            isDuplicated = dto.isDuplicated,
            isPlaceCountLimited = dto.isPlaceCountLimited,
            isActive = dto.isActive
        )
    }
}
