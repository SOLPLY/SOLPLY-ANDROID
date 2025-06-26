package com.teamsolply.solply.oauth.source

interface OauthLocalDataSource {
    suspend fun setAuthLocalData(accessToken: String, refreshToken: String)
}
