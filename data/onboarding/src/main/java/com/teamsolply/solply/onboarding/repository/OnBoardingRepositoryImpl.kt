package com.teamsolply.solply.onboarding.repository

import android.util.Log
import com.teamsolply.solply.onboarding.dto.request.PatchUserInfoRequestDto
import com.teamsolply.solply.onboarding.mapper.toEntity
import com.teamsolply.solply.onboarding.mapper.toTownEntities
import com.teamsolply.solply.onboarding.model.PersonaEntity
import com.teamsolply.solply.onboarding.model.TownEntity
import com.teamsolply.solply.onboarding.model.UserInfoEntity
import com.teamsolply.solply.onboarding.source.remote.OnBoardingRemoteDataSource
import javax.inject.Inject

class OnBoardingRepositoryImpl @Inject constructor(
    private val onBoardingRemoteDataSource: OnBoardingRemoteDataSource
) : OnBoardingRepository {
    override suspend fun getPersonaQuestions(): Result<PersonaEntity> = runCatching {
        onBoardingRemoteDataSource.getPersonaQuestions()
    }.mapCatching { responseDto ->
        responseDto.toEntity()
    }

    override suspend fun getAllTowns(): Result<List<TownEntity>> = runCatching {
        onBoardingRemoteDataSource.getAllTowns()
    }.mapCatching { responseDto ->
        responseDto.toTownEntities()
    }

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
