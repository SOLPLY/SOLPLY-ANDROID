package com.teamsolply.solply.course.datasource

import com.teamsolply.solply.course.service.CourseService
import com.teamsolply.solply.course.source.CourseRemoteDataSource
import javax.inject.Inject

class CourseRemoteDataSourceImpl @Inject constructor(
    private val courseService: CourseService
) : CourseRemoteDataSource {
    override suspend fun getUserInfo() = courseService.getUserInfo().data

    override suspend fun getRecommendedCourse(townId: Int) =
        courseService.getRecommendedCourseList(townId).data
}
