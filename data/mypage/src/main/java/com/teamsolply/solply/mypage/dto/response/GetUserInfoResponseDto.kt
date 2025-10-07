package com.teamsolply.solply.mypage.dto.response

import com.teamsolply.solply.model.Persona
import com.teamsolply.solply.mypage.model.SelectedTownInfo
import com.teamsolply.solply.mypage.model.UserInfo
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GetUserInfoResponseDto(
    @SerialName("userId")
    val userId: Long,
    @SerialName("nickname")
    val nickname: String,
    @SerialName("selectedTown")
    val selectedTown: SelectedTownDto,
    @SerialName("persona")
    val persona: Persona,
    @SerialName("profileImageUrl")
    val profileImageUrl: String?
)

@Serializable
data class SelectedTownDto(
    @SerialName("townId")
    val townId: Long,
    @SerialName("townName")
    val townName: String
)

fun GetUserInfoResponseDto.toDomain(): UserInfo =
    UserInfo(
        userId = userId,
        nickname = nickname,
        selectedTown = SelectedTownInfo(
            townId = selectedTown.townId,
            townName = selectedTown.townName
        ),
        persona = persona,
        profileImageUrl = profileImageUrl ?: "" // 서버 null → 도메인 기본값
    )
