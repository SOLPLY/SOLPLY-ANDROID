package com.teamsolply.solply.mypage.dto.request

import com.teamsolply.solply.mypage.model.WithdrawType
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DeleteUserRequestDto(
    @SerialName("withdrawReason")
    val withdrawType: WithdrawType,

    @SerialName("reasonText")
    val reasonText: String
)
