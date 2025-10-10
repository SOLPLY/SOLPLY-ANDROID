package com.teamsolply.solply.mypage.repository

import com.teamsolply.solply.mypage.model.PersonaEntity
import com.teamsolply.solply.mypage.model.PlaceInfoEntity
import com.teamsolply.solply.mypage.model.UserInfo
import com.teamsolply.solply.mypage.model.WithdrawEntity

interface MypageRepository {
    suspend fun getUserInfo(): Result<UserInfo>
    suspend fun getPlaceList(townId: Long): Result<List<PlaceInfoEntity>>
    suspend fun getPersonaList(): Result<List<PersonaEntity>>
    suspend fun checkNicknameDuplicate(nickname: String): Result<Boolean>
    suspend fun getWithdrawList(): Result<List<WithdrawEntity>>
}
