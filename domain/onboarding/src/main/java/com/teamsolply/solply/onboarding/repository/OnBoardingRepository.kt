package com.teamsolply.solply.onboarding.repository

import com.teamsolply.solply.onboarding.model.PersonaEntity
import com.teamsolply.solply.onboarding.model.TownEntity
import com.teamsolply.solply.onboarding.model.UserInfoEntity

interface OnBoardingRepository {
    suspend fun getPersonaQuestions(): Result<PersonaEntity>
    suspend fun getAllTowns(): Result<TownEntity>
    suspend fun checkNicknameDuplicate(nickname: String): Result<Boolean>
    suspend fun patchUserInfo(
        selectedTownId: Long,
        favoriteTownIdList: List<Long>,
        persona: String,
        nickname: String
    ): Result<UserInfoEntity>
}
