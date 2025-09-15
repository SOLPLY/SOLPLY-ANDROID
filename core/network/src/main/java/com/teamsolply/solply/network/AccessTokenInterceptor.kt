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
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class AccessTokenInterceptor @Inject constructor(
    private val dataStore: DataStore<SolplyTokenData>,
    private val tokenRefreshService: TokenRefreshService,
    @ApplicationContext private val context: Context
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()

        if (originalRequest.header("Authorization") != null) {
            return chain.proceed(originalRequest)
        }

        val tokenData = runBlocking {
            dataStore.data.first()
        }

        val newRequest = if (tokenData.accessToken.isNotEmpty()) {
            originalRequest.newBuilder()
                .addHeader("Authorization", "Bearer ${tokenData.accessToken}")
                .build()
        } else {
            originalRequest
        }

        Log.d(TAG, "Request URL: ${newRequest.url}")
        Log.d(
            TAG,
            "Authorization header: ${if (newRequest.header("Authorization") != null) "Bearer [REDACTED]" else "null"}"
        )

        val response = chain.proceed(newRequest)

        // 401 또는 403 에러가 발생했을 때 토큰 재발급 시도
        if ((response.code == 401 || response.code == 403) &&
            originalRequest.header("X-Token-Refresh-Attempt") == null &&
            !originalRequest.url.toString().contains("/api/auth/refresh")
        ) { // 리프레시 API는 제외

            Log.d(TAG, "Received ${response.code} error, attempting token refresh")

            return runBlocking {
                try {
                    val refreshToken = tokenData.refreshToken

                    if (refreshToken.isEmpty()) {
                        Log.w(TAG, "No refresh token available")
                        restartApp()
                        return@runBlocking response
                    }

                    Log.d(TAG, "Attempting token refresh")

                    val refreshResponse = tokenRefreshService.postRefreshJwtToken(
                        refreshToken = refreshToken
                    )

                    dataStore.updateData { old ->
                        old.copy(
                            accessToken = refreshResponse.data.accessToken,
                            refreshToken = refreshResponse.data.refreshToken
                        )
                    }

                    Log.d(TAG, "Token refresh successful, retrying original request")

                    // 새로운 토큰으로 원래 요청 재시도
                    val retryRequest = originalRequest.newBuilder()
                        .header("Authorization", "Bearer ${refreshResponse.data.accessToken}")
                        .header("X-Token-Refresh-Attempt", "true")
                        .build()

                    chain.proceed(retryRequest)
                } catch (e: Exception) {
                    Log.e(TAG, "Token refresh failed", e)

                    dataStore.updateData { old ->
                        old.copy(
                            accessToken = "",
                            refreshToken = "",
                            autoSignIn = false
                        )
                    }

                    restartApp()
                    response
                }
            }
        }

        return response
    }

    private fun restartApp() {
        try {
            Log.d(TAG, "Restarting app via ProcessPhoenix")
            ProcessPhoenix.triggerRebirth(context)
        } catch (e: Exception) {
            Log.e(TAG, "Failed to restart app", e)
        }
    }

    companion object {
        private const val TAG = "AccessTokenInterceptor"
    }
}
