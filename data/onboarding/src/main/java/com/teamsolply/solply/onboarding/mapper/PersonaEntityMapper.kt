package com.teamsolply.solply.onboarding.mapper

import com.teamsolply.solply.onboarding.dto.response.GetPersonaQuestionsResponseDto
import com.teamsolply.solply.onboarding.dto.response.PersonaDto
import com.teamsolply.solply.onboarding.model.Persona
import com.teamsolply.solply.onboarding.model.PersonaEntity

fun GetPersonaQuestionsResponseDto.toEntity(): PersonaEntity {
    return PersonaEntity(
        personaList = personaDtoList.map { it.toEntity() }
    )
}

fun PersonaDto.toEntity(): Persona {
    return Persona(
        personaType = this.personaType,
        description = this.description
    )
}
