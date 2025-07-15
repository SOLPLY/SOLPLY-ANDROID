package com.teamsolply.solply.onboarding.repository

interface OnBoardingRepository {
    suspend fun checkNicknameDuplicate(nickname: String): Result<Boolean>
}
