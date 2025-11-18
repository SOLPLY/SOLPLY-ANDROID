package com.teamsolply.solply.onboarding.dto.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PatchUserInfoRequestDto(
    @SerialName("selectedTownId")
    val selectedTownId: Long,

    @SerialName("persona")
    val persona: String,

    @SerialName("nickname")
    val nickname: String,

    @SerialName("policyAgreementInfos")
    val policyAgreementInfos: List<PolicyAgreementInfoDto>
)
