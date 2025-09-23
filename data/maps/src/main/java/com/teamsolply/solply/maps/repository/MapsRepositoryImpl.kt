package com.teamsolply.solply.maps.repository

import com.teamsolply.solply.maps.dto.request.FileDto
import com.teamsolply.solply.maps.dto.request.PresignedUrlsRequestDto
import com.teamsolply.solply.maps.dto.response.toEntity
import com.teamsolply.solply.maps.mapper.toCourseInfoEntityList
import com.teamsolply.solply.maps.mapper.toDto
import com.teamsolply.solply.maps.mapper.toEntity
import com.teamsolply.solply.maps.model.CourseDetailEntity
import com.teamsolply.solply.maps.model.CourseInfoEntity
import com.teamsolply.solply.maps.model.CourseSaveEntity
import com.teamsolply.solply.maps.model.CourseSaveResultEntity
import com.teamsolply.solply.maps.model.PlaceDetailEntity
import com.teamsolply.solply.maps.model.PresignedUrlsRequestEntity
import com.teamsolply.solply.maps.model.PresignedUrlsResponseEntity
import com.teamsolply.solply.maps.model.SavePlaceToCourseEntity
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
        candidatePlaceId: Long
    ): Result<List<CourseInfoEntity>> = runCatching {
        mapsRemoteDataSource.getAddMyCourse(townId = townId, candidatePlaceId = candidatePlaceId)
    }.mapCatching { resultDto ->
        resultDto.courses.toCourseInfoEntityList()
    }

    override suspend fun postPlaceToCourse(
        courseId: Long,
        placeId: Long
    ): Result<SavePlaceToCourseEntity> = runCatching {
        mapsRemoteDataSource.postPlaceToCourse(
            courseId = courseId,
            placeId = placeId
        )
    }.mapCatching { resultDto ->
        resultDto.toEntity()
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
            updatedCourseId = it.updatedCourseId,
            updatedCourseName = it.updatedCourseName,
            isNewCourse = it.isNewCourse
        )
    }

    override suspend fun postSaveNewCourse(
        courseSaveEntity: CourseSaveEntity
    ): Result<Long> = runCatching {
        mapsRemoteDataSource.postSaveNewCourse(
            courseSaveRequestDto = courseSaveEntity.toDto()
        ).courseId
    }

    override suspend fun postPresignedUrl(presignedUrlsRequestEntity: PresignedUrlsRequestEntity): Result<PresignedUrlsResponseEntity> =
        runCatching {
            mapsRemoteDataSource.postPresignedUrl(
                presignedUrlsRequestDto = PresignedUrlsRequestDto(
                    files = presignedUrlsRequestEntity.files.map { FileDto(it.fileName) }
                )
            )
        }.mapCatching { responseDto ->
            PresignedUrlsResponseEntity(
                presignedUrlInfos = responseDto.presignedUrlInfos.map { dto ->
                    com.teamsolply.solply.maps.model.PresignedUrlInfo(
                        originalFileName = dto.originalFileName,
                        tempFileKey = dto.tempFileKey,
                        presignedUrl = dto.presignedUrl,
                        expirationSeconds = dto.expirationSeconds
                    )
                }
            )
        }
}
