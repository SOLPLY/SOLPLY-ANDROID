package com.teamsolply.solply.oauth.model

data class TokenEntity(
    val accessToken: String,
    val refreshToken: String,
    val isNewUser: Boolean
)
