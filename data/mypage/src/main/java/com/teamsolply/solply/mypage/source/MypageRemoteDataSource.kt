package com.teamsolply.solply.mypage.source

interface MypageRemoteDataSource {
    suspend fun getPlaceTownList()
    suspend fun getCourseTownList()
    suspend fun getPlaceList()
    suspend fun getCourseList()
    suspend fun deletePlaces(placeList: List<Int>)
    suspend fun deleteCourses(courseList: List<Int>)
}
