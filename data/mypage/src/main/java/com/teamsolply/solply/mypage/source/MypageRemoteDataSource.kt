package com.teamsolply.solply.mypage.source

import com.teamsolply.solply.mypage.dto.response.CourseListResponseDto
import com.teamsolply.solply.mypage.dto.response.CourseTownListResponseDto
import com.teamsolply.solply.mypage.dto.response.PlaceListResponseDto
import com.teamsolply.solply.mypage.dto.response.PlaceTownListResponseDto

interface MypageRemoteDataSource {
    suspend fun getPlaceTownList(): PlaceTownListResponseDto
    suspend fun getCourseTownList(): CourseTownListResponseDto
    suspend fun getPlaceList(townId: Int): PlaceListResponseDto
    suspend fun getCourseList(townId: Int): CourseListResponseDto
    suspend fun deletePlaces(placeList: List<Int>)
    suspend fun deleteCourses(courseList: List<Int>)
}
