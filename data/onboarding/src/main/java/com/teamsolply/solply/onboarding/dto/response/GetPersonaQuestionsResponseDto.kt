package com.teamsolply.solply.onboarding.dto.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GetPersonaQuestionsResponseDto(
    @SerialName("personaList")
    val personaDtoList: List<PersonaDto>
)

@Serializable
data class PersonaDto(
    @SerialName("personaType")
    val personaType: String,
    @SerialName("description")
    val description: String
)
