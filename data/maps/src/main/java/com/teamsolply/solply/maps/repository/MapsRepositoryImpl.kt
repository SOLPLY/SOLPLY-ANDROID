package com.teamsolply.solply.maps.repository

import com.teamsolply.solply.maps.model.NewCourseEntity
import com.teamsolply.solply.maps.source.MapsRemoteDataSource
import javax.inject.Inject

class MapsRepositoryImpl @Inject constructor(
    private val mapsRemoteDataSource: MapsRemoteDataSource
) : MapsRepository {
    override suspend fun saveCourse(courseInfo: NewCourseEntity): Result<Unit> = runCatching {
        mapsRemoteDataSource.saveCourse(
            courseInfo = courseInfo.courseName
        )
    }
}
