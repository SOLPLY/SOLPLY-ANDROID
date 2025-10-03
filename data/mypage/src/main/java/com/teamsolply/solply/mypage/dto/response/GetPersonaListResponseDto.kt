package com.teamsolply.solply.mypage.dto.response

import com.teamsolply.solply.model.Persona
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GetPersonaListResponseDto(
    @SerialName("personaList")
    val personaDtoList: List<PersonaDto>
)

@Serializable
data class PersonaDto(
    @SerialName("personaType")
    val personaType: Persona,
    @SerialName("description")
    val description: String
)
