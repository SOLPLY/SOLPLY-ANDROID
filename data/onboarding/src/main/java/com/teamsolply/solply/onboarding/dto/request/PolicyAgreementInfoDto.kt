package com.teamsolply.solply.onboarding.dto.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PolicyAgreementInfoDto(
    @SerialName("policyId")
    val policyId: Long,

    @SerialName("isAgree")
    val isAgree: Boolean
)