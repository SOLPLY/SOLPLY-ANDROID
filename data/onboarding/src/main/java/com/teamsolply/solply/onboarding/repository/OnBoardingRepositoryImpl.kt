package com.teamsolply.solply.onboarding.repository

import com.teamsolply.solply.onboarding.dto.request.PatchUserInfoRequestDto
import com.teamsolply.solply.onboarding.model.UserInfoEntity
import com.teamsolply.solply.onboarding.source.OnBoardingRemoteDataSource
import javax.inject.Inject

class OnBoardingRepositoryImpl @Inject constructor(
    private val onBoardingRemoteDataSource: OnBoardingRemoteDataSource
) : OnBoardingRepository {
    override suspend fun checkNicknameDuplicate(nickname: String): Result<Boolean> = runCatching {
        onBoardingRemoteDataSource.checkNicknameDuplicate(nickname = nickname).isDuplicated
    }

    override suspend fun patchUserInfo(
        favoriteTown: Long,
        persona: String,
        nickname: String
    ): Result<UserInfoEntity> = runCatching {
        onBoardingRemoteDataSource.patchUserInfo(
            PatchUserInfoRequestDto(
                favoriteTown = favoriteTown,
                persona = persona,
                nickname = nickname
            )
        )
    }.mapCatching {
        UserInfoEntity(
            favoriteTownId = it.favoriteTownId,
            favoriteTownName = it.favoriteTownName,
            persona = it.persona,
            nickname = it.nickname
        )
    }
}
