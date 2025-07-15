package com.teamsolply.solply.onboarding.repository

import com.teamsolply.solply.onboarding.source.OnBoardingRemoteDataSource
import javax.inject.Inject

class OnBoardingRepositoryImpl @Inject constructor(
    private val onBoardingRemoteDataSource: OnBoardingRemoteDataSource
) : OnBoardingRepository {
    override suspend fun checkNicknameDuplicate(nickname: String): Result<Boolean> = runCatching {
        onBoardingRemoteDataSource.checkNicknameDuplicate(nickname = nickname).isDuplicated
    }
}
