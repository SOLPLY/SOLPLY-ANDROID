package com.teamsolply.solply.mypage.datasource

import com.teamsolply.solply.mypage.service.MypageService
import com.teamsolply.solply.mypage.source.MypageRemoteDataSource
import javax.inject.Inject

class MypageRemoteDataSourceImpl @Inject constructor(
    private val mypageService: MypageService
) : MypageRemoteDataSource {
    override suspend fun getPlaceTownList() {
        mypageService.getPlaceTownList()
    }

    override suspend fun getCourseTownList() {
        TODO("Not yet implemented")
    }

    override suspend fun getPlaceList() {
        TODO("Not yet implemented")
    }

    override suspend fun getCourseList() {
        TODO("Not yet implemented")
    }

    override suspend fun deletePlaces() {
        TODO("Not yet implemented")
    }

    override suspend fun deleteCourses() {
        TODO("Not yet implemented")
    }
}
