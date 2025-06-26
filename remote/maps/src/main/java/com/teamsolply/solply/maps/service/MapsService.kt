package com.teamsolply.solply.maps.service

import com.teamsolply.solply.maps.dto.request.SaveCourseRequestDto

interface MapsService {
    suspend fun saveCourse(
        saveCourseRequestDto: SaveCourseRequestDto
    )
}