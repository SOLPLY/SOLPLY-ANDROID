package com.teamsolply.solply.onboarding.model

data class TownEntity(
    val selectedTown: SubTownEntity?,
    val favoriteTownList: List<SubTownEntity>
)

data class SubTownEntity(
    val townId: Long,
    val townName: String
)
