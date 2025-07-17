package com.teamsolply.solply.maps.source

import com.teamsolply.solply.maps.dto.request.CourseSaveRequestDto
import com.teamsolply.solply.maps.dto.response.CourseDetailResponseDto
import com.teamsolply.solply.maps.dto.response.CourseSaveResponseDto
import com.teamsolply.solply.maps.dto.response.CoursesResponseDto
import com.teamsolply.solply.maps.dto.response.GetPlaceDetailResponseDto

interface MapsRemoteDataSource {
    // 장소 상세
    suspend fun getPlaceDetail(placeId: Long): GetPlaceDetailResponseDto
    suspend fun savePlaceBookMark(placeId: Long)
    suspend fun deletePlaceBookMark(placeId: Long)
    suspend fun getAddMyCourse(
        townId: Long,
        placeId: Long
    ): CoursesResponseDto

    // 코스 상세
    suspend fun getCourseDetail(courseId: Long): CourseDetailResponseDto
    suspend fun postCourseBookMark(courseId: Long)
    suspend fun deleteCourseBookMark(courseId: Long)

    // 코스 편집
    suspend fun putEditCourse(courseId: Long, courseSaveRequestDto: CourseSaveRequestDto): CourseSaveResponseDto
}
