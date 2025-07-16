package com.teamsolply.solply.course.model

data class UserEntity(
    val userId: Int,
    val nickname: String,
    val selectedTown: TownEntity,
    val persona: String
)
