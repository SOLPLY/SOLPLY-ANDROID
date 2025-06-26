package com.teamsolply.solply.onboarding.source

interface OnBoardingRemoteDataSource {
    suspend fun signUp(nickname: String, id: Int)
}