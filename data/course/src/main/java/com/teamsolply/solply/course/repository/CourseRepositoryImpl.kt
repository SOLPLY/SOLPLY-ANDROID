package com.teamsolply.solply.course.repository

import android.util.Log
import com.teamsolply.solply.course.model.CourseEntity
import com.teamsolply.solply.course.model.TownEntity
import com.teamsolply.solply.course.model.UserEntity
import com.teamsolply.solply.course.source.CourseRemoteDataSource
import javax.inject.Inject

class CourseRepositoryImpl @Inject constructor(
    private val courseRemoteDataSource: CourseRemoteDataSource
) : CourseRepository {
    override suspend fun getUserInfo() = runCatching {
        courseRemoteDataSource.getUserInfo()
    }.mapCatching { user ->
        UserEntity(
            userId = user.userId,
            nickname = user.nickname,
            persona = user.persona,
            selectedTown = TownEntity(
                townId = user.selectedTown.townId,
                townName = user.selectedTown.townName
            )
        )
    }

    override suspend fun getRecommendedCourse(townId: Int) = runCatching {
        courseRemoteDataSource.getRecommendedCourse(townId).courseList
    }.mapCatching { courseList ->
        courseList.map { course ->
            CourseEntity(
                courseId = course.courseId,
                courseName = course.courseName,
                imageUrl = course.imageUrl,
                tagList = course.tagList,
                isSaved = course.isSaved
            )
        }
    }
}
