package com.teamsolply.solply.onboarding.mapper

import com.teamsolply.solply.onboarding.dto.response.GetAllTownResponseDto
import com.teamsolply.solply.onboarding.dto.response.TownDto
import com.teamsolply.solply.onboarding.model.SubTownEntity
import com.teamsolply.solply.onboarding.model.TownEntity

fun GetAllTownResponseDto.toEntity(): TownEntity {
    return TownEntity(
        selectedTown = selectedTown?.toSubEntity(),
        favoriteTownList = favoriteTownList.map { it.toSubEntity() }
    )
}

fun TownDto.toSubEntity(): SubTownEntity {
    return SubTownEntity(
        townId = this.townId,
        townName = this.townName
    )
}
