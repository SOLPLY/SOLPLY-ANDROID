package com.teamsolply.solply.mypage.source

import com.teamsolply.solply.mypage.dto.response.PlaceTownListResponseDto

interface MypageRemoteDataSource {
    suspend fun getPlaceTownList(): PlaceTownListResponseDto
    suspend fun getCourseTownList()
    suspend fun getPlaceList()
    suspend fun getCourseList()
    suspend fun deletePlaces(placeList: List<Int>)
    suspend fun deleteCourses(courseList: List<Int>)
}
