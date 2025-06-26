package com.teamsolply.solply.maps.datasource

import com.teamsolply.solply.maps.dto.request.SaveCourseRequestDto
import com.teamsolply.solply.maps.service.MapsService
import com.teamsolply.solply.maps.source.MapsRemoteDataSource
import javax.inject.Inject

class MapsRemoteDataSourceImpl @Inject constructor(
    private val mapsService: MapsService
): MapsRemoteDataSource {
    override suspend fun saveCourse(courseInfo: String) = mapsService.saveCourse(
        SaveCourseRequestDto(courseName = courseInfo)
    )
}