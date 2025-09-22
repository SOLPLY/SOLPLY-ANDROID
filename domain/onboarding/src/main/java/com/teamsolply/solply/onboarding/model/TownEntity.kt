package com.teamsolply.solply.onboarding.model

data class TownEntity(
    val towns: List<SubTownEntity>
)

data class SubTownEntity(
    val townId: Long,
    val townName: String,
    val subTowns: List<SubTownEntity>? = null
)
