package com.teamsolply.solply.mypage.datasource

import com.teamsolply.solply.mypage.dto.response.GetPersonaListResponseDto
import com.teamsolply.solply.mypage.dto.response.GetUserInfoResponseDto
import com.teamsolply.solply.mypage.service.MypageService
import javax.inject.Inject

class MypageRemoteDataSourceImpl @Inject constructor(
    private val mypageService: MypageService
) : MypageRemoteDataSource {
    override suspend fun getUserInfo(): GetUserInfoResponseDto {
        return mypageService.getUserInfo().data
    }

    override suspend fun getPlaceList(townId: Long) =
        mypageService.getPlaceList(townId = townId).data

    override suspend fun getPersonaList(): GetPersonaListResponseDto {
        return mypageService.getPersonaList().data
    }
}
