package com.teamsolply.solply.mypage.repository

import com.teamsolply.solply.mypage.model.PlaceInfoEntity
import com.teamsolply.solply.mypage.model.UserInfo

interface MypageRepository {
    suspend fun getUserInfo(): Result<UserInfo>
    suspend fun getPlaceList(townId: Long): Result<List<PlaceInfoEntity>>
}
