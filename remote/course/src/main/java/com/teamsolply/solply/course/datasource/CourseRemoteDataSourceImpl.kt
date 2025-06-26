package com.teamsolply.solply.course.datasource

import com.teamsolply.solply.course.service.CourseService
import com.teamsolply.solply.course.source.CourseRemoteDataSource
import javax.inject.Inject

class CourseRemoteDataSourceImpl @Inject constructor(
    private val courseService: CourseService
) : CourseRemoteDataSource {
    override suspend fun getRecommendedCourse(): String =
        courseService.getRecommendedCourse().data.courseName
}