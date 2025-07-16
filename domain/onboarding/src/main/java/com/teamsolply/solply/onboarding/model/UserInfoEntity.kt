package com.teamsolply.solply.onboarding.model

data class UserInfoEntity(
    val selectedTownId: Long?,
    val selectedTownName: String,
    val persona: String,
    val nickname: String
)
