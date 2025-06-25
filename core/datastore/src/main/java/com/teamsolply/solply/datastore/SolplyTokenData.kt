package com.teamsolply.solply.datastore

import kotlinx.serialization.Serializable

@Serializable
data class SolplyTokenData(
    val accessToken: String = "",
    val refreshToken: String = "",
    val autoSignIn: Boolean = false
)
