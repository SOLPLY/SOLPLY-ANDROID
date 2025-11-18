package com.teamsolply.solply.onboarding.repository

import com.teamsolply.solply.onboarding.dto.request.PatchUserInfoRequestDto
import com.teamsolply.solply.onboarding.mapper.toDto
import com.teamsolply.solply.onboarding.mapper.toEntity
import com.teamsolply.solply.onboarding.model.PersonaEntity
import com.teamsolply.solply.onboarding.model.PolicyAgreementInfoEntity
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

    override suspend fun getAllTowns(): Result<TownEntity> = runCatching {
        onBoardingRemoteDataSource.getAllTowns()
    }.mapCatching { responseDto ->
        responseDto.toEntity()
    }

    override suspend fun checkNicknameDuplicate(nickname: String): Result<Boolean> = runCatching {
        onBoardingRemoteDataSource.checkNicknameDuplicate(nickname = nickname).isDuplicated
    }

    override suspend fun patchUserInfo(
        selectedTownId: Long,
        persona: String,
        nickname: String,
        policyAgreementInfos: List<PolicyAgreementInfoEntity>
    ): Result<UserInfoEntity> = runCatching {
        onBoardingRemoteDataSource.patchUserInfo(
            PatchUserInfoRequestDto(
                selectedTownId = selectedTownId,
                persona = persona,
                nickname = nickname,
                policyAgreementInfos = policyAgreementInfos.map { it.toDto() }
            )
        )
    }.mapCatching {
        UserInfoEntity(
            selectedTownId = it.selectedTownId,
            selectedTownName = it.selectedTownName,
            persona = it.persona,
            nickname = it.nickname
        )
    }
}
