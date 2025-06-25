package com.teamsolply.solply.oauth.source

import com.teamsolply.solply.datastore.SolplyTokenData

interface OauthLocalDataSource {
    suspend fun setAuthLocalData(jwtToken: SolplyTokenData)
}
