package com.teamsolply.solply.onboarding.mapper

import com.teamsolply.solply.onboarding.dto.request.PolicyAgreementInfoDto
import com.teamsolply.solply.onboarding.model.PolicyAgreementInfoEntity

fun PolicyAgreementInfoEntity.toDto(): PolicyAgreementInfoDto =
    PolicyAgreementInfoDto(
        policyId = this.policyId,
        isAgree = this.isAgree
    )
