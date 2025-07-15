package com.teamsolply.solply.mypage.datasource

import com.teamsolply.solply.mypage.service.MypageService
import com.teamsolply.solply.mypage.source.MypageRemoteDataSource
import javax.inject.Inject

class MypageRemoteDataSourceImpl @Inject constructor(
    private val mypageService: MypageService
) : MypageRemoteDataSource {
    override suspend fun getPlaceTownList() = mypageService.getPlaceTownList().data

    override suspend fun getCourseTownList() = mypageService.getCourseTownList().data
    // = mypageService.getCourseTownList().data

    override suspend fun getPlaceList(townId: Int) =
        mypageService.getPlaceList(townId = townId).data

    override suspend fun getCourseList(townId: Int) =
        mypageService.getCourseList(townId = townId).data

    override suspend fun deletePlaces(placeList: List<Int>) = mypageService.deletePlaces(placeList)

    override suspend fun deleteCourses(courseList: List<Int>) =
        mypageService.deleteCourses(courseList)
}
