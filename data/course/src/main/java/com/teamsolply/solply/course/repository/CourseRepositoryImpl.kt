package com.teamsolply.solply.course.repository

import com.teamsolply.solply.course.model.CourseEntity
import com.teamsolply.solply.course.source.CourseRemoteDataSource
import javax.inject.Inject

class CourseRepositoryImpl @Inject constructor(
    private val courseRemoteDataSource: CourseRemoteDataSource
) : CourseRepository {
    override suspend fun getRecommendedCourse(): Result<CourseEntity> = runCatching {
        courseRemoteDataSource.getRecommendedCourse()
    }.mapCatching { CourseEntity(it) }
}