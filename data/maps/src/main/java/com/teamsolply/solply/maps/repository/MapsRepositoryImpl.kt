package com.teamsolply.solply.maps.repository

import com.teamsolply.solply.maps.mapper.toDto
import com.teamsolply.solply.maps.mapper.toEntity
import com.teamsolply.solply.maps.model.CourseDetailEntity
import com.teamsolply.solply.maps.model.CourseInfoEntity
import com.teamsolply.solply.maps.model.CourseSaveEntity
import com.teamsolply.solply.maps.model.CourseSaveResultEntity
import com.teamsolply.solply.maps.model.PlaceDetailEntity
import com.teamsolply.solply.maps.source.MapsRemoteDataSource
import toEntity
import javax.inject.Inject

class MapsRepositoryImpl @Inject constructor(
    private val mapsRemoteDataSource: MapsRemoteDataSource
) : MapsRepository {
    // Add Place
    override suspend fun getPlaceDetail(placeId: Long): Result<PlaceDetailEntity> = runCatching {
        mapsRemoteDataSource.getPlaceDetail(placeId = placeId)
    }.mapCatching { responseDto ->
        responseDto.toEntity()
    }

    override suspend fun savePlaceBookMark(placeId: Long): Result<Unit> = runCatching {
        mapsRemoteDataSource.savePlaceBookMark(placeId = placeId)
    }

    override suspend fun deletePlaceBookMark(placeId: Long): Result<Unit> = runCatching {
        mapsRemoteDataSource.deletePlaceBookMark(placeId = placeId)
    }

    override suspend fun getAddMyCourse(
        townId: Long,
        placeId: Long
    ): Result<List<CourseInfoEntity>> = runCatching {
        mapsRemoteDataSource.getAddMyCourse(townId = townId, placeId = placeId)
    }.mapCatching { resultDto ->
        resultDto.courses.map { it.toEntity() }
    }

    // Add Course
    override suspend fun getCourseDetail(courseId: Long): Result<CourseDetailEntity> = runCatching {
        mapsRemoteDataSource.getCourseDetail(courseId = courseId)
    }.mapCatching { dto ->
        dto.toEntity()
    }

    override suspend fun postCourseBookMark(courseId: Long): Result<Unit> = runCatching {
        mapsRemoteDataSource.postCourseBookMark(courseId = courseId)
    }

    override suspend fun deleteCourseBookMark(courseId: Long): Result<Unit> = runCatching {
        mapsRemoteDataSource.deleteCourseBookMark(courseId = courseId)
    }

    override suspend fun putEditCourse(
        courseId: Long,
        courseSaveEntity: CourseSaveEntity
    ): Result<CourseSaveResultEntity> = runCatching {
        mapsRemoteDataSource.putEditCourse(
            courseId = courseId,
            courseSaveRequestDto = courseSaveEntity.toDto()
        )
    }.mapCatching {
        CourseSaveResultEntity(
            courseId = it.courseId,
            isNewCourse = it.isNewCourse
        )
    }
}
