package com.teamsolply.solply.maps.repository

import com.teamsolply.solply.maps.mapper.toEntity
import com.teamsolply.solply.maps.model.CourseDetailEntity
import com.teamsolply.solply.maps.model.CourseInfoEntity
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
}
