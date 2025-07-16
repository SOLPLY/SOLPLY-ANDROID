package com.teamsolply.solply.onboarding.mapper

import com.teamsolply.solply.onboarding.dto.response.GetAllTownResponseDto
import com.teamsolply.solply.onboarding.dto.response.MainTownDto
import com.teamsolply.solply.onboarding.dto.response.SubTownDto
import com.teamsolply.solply.onboarding.model.SubTownEntity
import com.teamsolply.solply.onboarding.model.TownEntity

fun GetAllTownResponseDto.toTownEntities(): List<TownEntity> {
    return mainTownDto.map { it.toTownEntity() }
}

fun MainTownDto.toTownEntity(): TownEntity {
    return TownEntity(
        townId = this.townId,
        townName = this.townName,
        subTowns = this.subTowns?.map { it.toSubTownEntity() }
    )
}

fun SubTownDto.toSubTownEntity(): SubTownEntity {
    return SubTownEntity(
        townId = this.townId,
        townName = this.townName,
        subTowns = this.subTowns?.map { it.toSubTownEntity() }
    )
}
