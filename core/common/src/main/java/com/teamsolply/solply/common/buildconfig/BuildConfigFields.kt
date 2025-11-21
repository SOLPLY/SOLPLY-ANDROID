package com.teamsolply.solply.common.buildconfig

data class BuildConfigFields(
    val baseUrl: String,
    val kakaoNativeKey: String,
    val googleWebClientId: String,
    val naverClientId: String,
    val naverDevelopersClientId: String,
    val naverDevelopersClientSecret: String,
    val isDebug: Boolean
)
