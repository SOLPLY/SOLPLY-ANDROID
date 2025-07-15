package com.teamsolply.solply.mypage.repository

import com.teamsolply.solply.mypage.model.CourseInfoEntity
import com.teamsolply.solply.mypage.model.PlaceInfoEntity
import com.teamsolply.solply.mypage.model.CourseTownEntity
import com.teamsolply.solply.mypage.model.PlaceTownEntity

interface MypageRepository {
    suspend fun getPlaceTownList(): Result<List<PlaceTownEntity>>
    suspend fun getCourseTownList(): Result<List<CourseTownEntity>>
    suspend fun getPlaceList(): Result<List<PlaceInfoEntity>>
    suspend fun getCourseList(): Result<List<CourseInfoEntity>>

    suspend fun deletePlaces(placeIds: List<Int>): Result<Unit>
    suspend fun deleteCourses(courseIds: List<Int>): Result<Unit>
}
