package com.teamsolply.solply.onboarding.model

data class TownEntity(
    val parentTowns: List<ParentTownEntity>
)

data class ParentTownEntity(
    val townId: Long,
    val townName: String,
    val subTowns: List<SubTownEntity>
)

data class SubTownEntity(
    val townId: Long,
    val townName: String
)