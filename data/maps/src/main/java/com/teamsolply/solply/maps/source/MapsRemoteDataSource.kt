package com.teamsolply.solply.maps.source

import com.teamsolply.solply.maps.dto.request.CourseSaveRequestDto
import com.teamsolply.solply.maps.dto.request.PresignedUrlsRequestDto
import com.teamsolply.solply.maps.dto.request.ReportWrongPlaceRequestDto
import com.teamsolply.solply.maps.dto.response.CourseDetailResponseDto
import com.teamsolply.solply.maps.dto.response.CourseSaveResponseDto
import com.teamsolply.solply.maps.dto.response.CoursesResponseDto
import com.teamsolply.solply.maps.dto.response.GetPlaceDetailResponseDto
import com.teamsolply.solply.maps.dto.response.NewCourseResponseDto
import com.teamsolply.solply.maps.dto.response.PresignedUrlsResponseDto
import com.teamsolply.solply.maps.dto.response.ReportWrongPlaceResponseDto
import com.teamsolply.solply.maps.dto.response.SavePlaceToCourseResponseDto

interface MapsRemoteDataSource {
    // 장소 상세
    suspend fun getPlaceDetail(placeId: Long): GetPlaceDetailResponseDto
    suspend fun savePlaceBookMark(placeId: Long)
    suspend fun deletePlaceBookMark(placeId: Long)
    suspend fun getAddMyCourse(
        townId: Long,
        candidatePlaceId: Long
    ): CoursesResponseDto

    suspend fun postPlaceToCourse(
        courseId: Long,
        placeId: Long
    ): SavePlaceToCourseResponseDto

    // 코스 상세
    suspend fun getCourseDetail(courseId: Long): CourseDetailResponseDto
    suspend fun postCourseBookMark(courseId: Long)
    suspend fun deleteCourseBookMark(courseId: Long)

    // 코스 편집
    suspend fun putEditCourse(
        courseId: Long,
        courseSaveRequestDto: CourseSaveRequestDto
    ): CourseSaveResponseDto

    suspend fun postSaveNewCourse(
        courseSaveRequestDto: CourseSaveRequestDto
    ): NewCourseResponseDto

    // 제보하기
    suspend fun postPresignedUrl(
        presignedUrlsRequestDto: PresignedUrlsRequestDto
    ): PresignedUrlsResponseDto

    suspend fun postReportWrongPlace(
        placeId: Long,
        reportWrongPlaceRequestDto: ReportWrongPlaceRequestDto
    ): ReportWrongPlaceResponseDto
}
