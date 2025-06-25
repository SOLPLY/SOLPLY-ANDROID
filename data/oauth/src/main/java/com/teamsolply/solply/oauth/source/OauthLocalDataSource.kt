package com.teamsolply.solply.oauth.source

import com.teamsolply.solply.datastore.SolplyTokenData
import kotlinx.coroutines.flow.Flow

interface OauthLocalDataSource {
    suspend fun setAuthLocalData(jwtToken: SolplyTokenData)
}