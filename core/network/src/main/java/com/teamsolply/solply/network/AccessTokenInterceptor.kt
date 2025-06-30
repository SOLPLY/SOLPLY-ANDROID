package com.teamsolply.solply.network

import android.annotation.SuppressLint
import android.util.Log
import androidx.datastore.core.DataStore
import com.teamsolply.solply.datastore.SolplyTokenData
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class AccessTokenInterceptor @Inject constructor(
    private val dataStore: DataStore<SolplyTokenData>
) : Interceptor {

    @SuppressLint("LogTagMismatch")
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()

        if (originalRequest.header("Authorization") != null) {
            return chain.proceed(originalRequest)
        }

        val accessToken = runBlocking {
            dataStore.data.first().accessToken
        }

        val newRequest = if (accessToken.isNotEmpty()) {
            originalRequest.newBuilder()
                .addHeader("Authorization", "Bearer $accessToken")
                .build()
        } else {
            originalRequest
        }

        if (Log.isLoggable("AccessTokenInterceptor", Log.DEBUG)) {
            Log.d("AccessTokenInterceptor", "Request URL: ${newRequest.url}")
            Log.d("AccessTokenInterceptor", "Authorization header: ${newRequest.header("Authorization")}")
        }
        return chain.proceed(newRequest)
    }
}
