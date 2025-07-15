package com.teamsolply.solply.mypage.service

interface MypageService {
    suspend fun getPlaceTownList()
    suspend fun getCourseTownList()
    suspend fun getPlaceList()
    suspend fun getCourseList()

    suspend fun deletePlaces(placeIds: List<Int>)
    suspend fun deleteCourses(courseIds: List<Int>)
}
