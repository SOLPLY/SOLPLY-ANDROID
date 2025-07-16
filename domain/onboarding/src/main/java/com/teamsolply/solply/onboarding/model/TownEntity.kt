package com.teamsolply.solply.onboarding.model

data class TownEntity(
    val townId: Long,
    val townName: String,
    val subTowns: List<SubTownEntity>? = null
)

data class SubTownEntity(
    val townId: Long,
    val townName: String,
    val subTowns: List<SubTownEntity>? = null
)
