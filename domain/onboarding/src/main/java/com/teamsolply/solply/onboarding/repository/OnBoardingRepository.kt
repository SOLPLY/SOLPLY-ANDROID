package com.teamsolply.solply.onboarding.repository

import com.teamsolply.solply.onboarding.model.UserInfoEntity

interface OnBoardingRepository {
    suspend fun checkNicknameDuplicate(nickname: String): Result<Boolean>
    suspend fun patchUserInfo(
        favoriteTown: Long,
        persona: String,
        nickname: String
    ): Result<UserInfoEntity>
}
