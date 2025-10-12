package com.teamsolply.solply.mypage.dto.response

import com.teamsolply.solply.mypage.model.WithdrawType
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GetWithdrawListResponseDto(
    @SerialName("description")
    val withdrawList: List<WithdrawResponseDto>
)

@Serializable
data class WithdrawResponseDto(
    @SerialName("withdrawType")
    val withdrawType: WithdrawType,

    @SerialName("description")
    val description: String
)
