package com.teamsolply.solply.onboarding.mapper

import com.teamsolply.solply.onboarding.dto.response.GetAllTownResponseDto
import com.teamsolply.solply.onboarding.dto.response.TownDto
import com.teamsolply.solply.onboarding.model.ParentTownEntity
import com.teamsolply.solply.onboarding.model.SubTownEntity
import com.teamsolply.solply.onboarding.model.TownEntity

fun GetAllTownResponseDto.toEntity(): TownEntity {
    val parentTowns = towns.filter { it.parentTownId == null }

    val grouped = parentTowns.map { parent ->
        ParentTownEntity(
            townId = parent.townId,
            townName = parent.townName,
            subTowns = towns
                .filter { it.parentTownId == parent.townId }
                .map { dto ->
                    SubTownEntity(
                        townId = dto.townId,
                        townName = dto.townName
                    )
                }
        )
    }

    return TownEntity(
        parentTowns = grouped
    )
}

fun TownDto.toParentEntity(): ParentTownEntity {
    return ParentTownEntity(
        townId = townId,
        townName = townName,
        subTowns = subTowns?.map { it.toSubEntity() } ?: emptyList()
    )
}

fun TownDto.toSubEntity(): SubTownEntity {
    return SubTownEntity(
        townId = townId,
        townName = townName
    )
}
