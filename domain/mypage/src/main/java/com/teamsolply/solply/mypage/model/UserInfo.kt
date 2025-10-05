package com.teamsolply.solply.mypage.model

import com.teamsolply.solply.model.Persona

data class UserInfo(
    val userId: Long,
    val nickname: String,
    val selectedTown: SelectedTownInfo,
    val persona: Persona,
    val profileImageUrl: String = ""
)

data class SelectedTownInfo(
    val townId: Long,
    val townName: String
)
