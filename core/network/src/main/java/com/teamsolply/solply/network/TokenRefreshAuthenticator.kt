package com.teamsolply.solply.network

import android.content.Context
import android.util.Log
import androidx.datastore.core.DataStore
import com.jakewharton.processphoenix.ProcessPhoenix
import com.teamsolply.solply.datastore.SolplyTokenData
import com.teamsolply.solply.network.service.TokenRefreshService
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route
import javax.inject.Inject

class TokenRefreshAuthenticator @Inject constructor(
    private val dataStore: DataStore<SolplyTokenData>,
    private val tokenRefreshService: TokenRefreshService,
    @ApplicationContext private val context: Context,
) : Authenticator {

    override fun authenticate(route: Route?, response: Response): Request? {
        if (response.code != 401) {
            return null
        }

        if (response.request.header("X-Token-Refresh-Attempt") != null) {
            Log.w(
                "TokenRefreshAuthenticator",
                "Token refresh already attempted, redirecting to login"
            )
            restartApp()
            return null
        }

        return runBlocking {
            try {
                val tokenData = dataStore.data.first()
                val refreshToken = tokenData.refreshToken

                if (refreshToken.isEmpty()) {
                    Log.w("TokenRefreshAuthenticator", "No refresh token available")
                    restartApp()
                    return@runBlocking null
                }

                Log.d("TokenRefreshAuthenticator", "Attempting token refresh")

                val refreshResponse = tokenRefreshService.postRefreshJwtToken(
                    refreshToken = "Bearer $refreshToken"
                )

                dataStore.updateData { old ->
                    old.copy(
                        accessToken = refreshResponse.accessToken,
                        refreshToken = refreshResponse.refreshToken
                    )
                }

                Log.d("TokenRefreshAuthenticator", "Token refresh successful")

                response.request.newBuilder()
                    .header("Authorization", "Bearer ${refreshResponse.accessToken}")
                    .header("X-Token-Refresh-Attempt", "true")
                    .build()

            } catch (e: Exception) {
                Log.e("TokenRefreshAuthenticator", "Token refresh failed", e)

                dataStore.updateData { old ->
                    old.copy(
                        accessToken = "",
                        refreshToken = "",
                        autoSignIn = false
                    )
                }

                restartApp()
                null
            }
        }
    }

    private fun restartApp() {
        try {
            Log.d("TokenRefreshAuthenticator", "Restarting app via ProcessPhoenix")
            ProcessPhoenix.triggerRebirth(context)
        } catch (e: Exception) {
            Log.e("TokenRefreshAuthenticator", "Failed to restart app", e)
        }
    }
}