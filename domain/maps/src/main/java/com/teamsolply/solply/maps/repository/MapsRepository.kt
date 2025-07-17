package com.teamsolply.solply.maps.repository

import com.teamsolply.solply.maps.model.CourseDetailEntity
import com.teamsolply.solply.maps.model.CourseInfoEntity
import com.teamsolply.solply.maps.model.CourseSaveEntity
import com.teamsolply.solply.maps.model.CourseSaveResultEntity
import com.teamsolply.solply.maps.model.PlaceDetailEntity

interface MapsRepository {
    // Add Place
    suspend fun getPlaceDetail(placeId: Long): Result<PlaceDetailEntity>
    suspend fun savePlaceBookMark(placeId: Long): Result<Unit>
    suspend fun deletePlaceBookMark(placeId: Long): Result<Unit>

    suspend fun getAddMyCourse(
        townId: Long,
        placeId: Long
    ): Result<List<CourseInfoEntity>>

    // Add Course
    suspend fun getCourseDetail(courseId: Long): Result<CourseDetailEntity>
    suspend fun postCourseBookMark(courseId: Long): Result<Unit>
    suspend fun deleteCourseBookMark(courseId: Long): Result<Unit>

    // Edit Course
    suspend fun putEditCourse(
        courseId: Long,
        courseSaveEntity: CourseSaveEntity
    ): Result<CourseSaveResultEntity>
}
