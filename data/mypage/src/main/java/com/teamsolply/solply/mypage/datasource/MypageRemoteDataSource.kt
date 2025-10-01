package com.teamsolply.solply.mypage.datasource

import com.teamsolply.solply.mypage.dto.response.GetPersonaListResponseDto
import com.teamsolply.solply.mypage.dto.response.GetUserInfoResponseDto
import com.teamsolply.solply.mypage.dto.response.PlaceListResponseDto

interface MypageRemoteDataSource {
    suspend fun getUserInfo(): GetUserInfoResponseDto
    suspend fun getPlaceList(townId: Long): PlaceListResponseDto
    suspend fun getPersonaList(): GetPersonaListResponseDto
}
