package com.teamsolply.solply.maps.datasource

import com.teamsolply.solply.maps.dto.request.CourseSaveRequestDto
import com.teamsolply.solply.maps.dto.response.CourseDetailResponseDto
import com.teamsolply.solply.maps.dto.response.CourseSaveResponseDto
import com.teamsolply.solply.maps.dto.response.CoursesResponseDto
import com.teamsolply.solply.maps.dto.response.GetPlaceDetailResponseDto
import com.teamsolply.solply.maps.service.MapsService
import com.teamsolply.solply.maps.source.MapsRemoteDataSource
import javax.inject.Inject

class MapsRemoteDataSourceImpl @Inject constructor(
    private val mapsService: MapsService
) : MapsRemoteDataSource {

    override suspend fun getPlaceDetail(placeId: Long): GetPlaceDetailResponseDto =
        mapsService.getPlaceDetail(placeId = placeId).data

    override suspend fun savePlaceBookMark(placeId: Long): Unit =
        mapsService.postPlaceBookMark(placeId = placeId).data

    override suspend fun deletePlaceBookMark(placeId: Long): Unit =
        mapsService.deletePlaceBookMark(placeId = placeId).data

    override suspend fun getAddMyCourse(townId: Long, placeId: Long): CoursesResponseDto =
        mapsService.getAddMyCourse(townId = townId, placeId = placeId).data

    override suspend fun getCourseDetail(courseId: Long): CourseDetailResponseDto =
        mapsService.getCourseDetail(courseId = courseId).data

    override suspend fun postCourseBookMark(courseId: Long): Unit =
        mapsService.postCourseBookMark(courseId = courseId).data

    override suspend fun deleteCourseBookMark(courseId: Long): Unit =
        mapsService.deleteCourseBookMark(courseId = courseId).data

    override suspend fun putEditCourse(
        courseId: Long,
        courseSaveRequestDto: CourseSaveRequestDto
    ): CourseSaveResponseDto =
        mapsService.putEditCourse(
            courseId = courseId,
            courseSaveRequestDto = courseSaveRequestDto
        ).data
}
