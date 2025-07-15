package com.teamsolply.solply.onboarding.model

data class PersonaEntity(
    val personaList: List<Persona>

)

data class Persona(
    val personaType: String,
    val description: String
)
