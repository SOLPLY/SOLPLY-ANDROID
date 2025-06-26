package com.teamsolply.solply.mypage.repository

import com.teamsolply.solply.mypage.model.UserInfoEntity

interface MypageRepository {
    suspend fun getUserInfo(): Result<UserInfoEntity>
}
