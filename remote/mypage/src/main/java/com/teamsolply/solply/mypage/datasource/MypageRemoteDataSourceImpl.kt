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
}
