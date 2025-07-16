package com.teamsolply.solply.place.model

data class UserInfo(
    val userId: Long,
    val nickname: String,
    val selectedTown: SelectedTownInfo,
    val persona: String
)

data class SelectedTownInfo(
    val townId: Long,
    val townName: String
)
