package com.teamsolply.solply.network.di

import android.content.Context
import android.util.Log
import androidx.datastore.core.DataStore
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.teamsolply.solply.common.buildconfig.BuildConfigFieldProvider
import com.teamsolply.solply.datastore.SolplyTokenData
import com.teamsolply.solply.network.AccessTokenInterceptor
import com.teamsolply.solply.network.TokenRefreshAuthenticator
import com.teamsolply.solply.network.service.TokenRefreshService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideJson(): Json = Json {
        ignoreUnknownKeys = true
        coerceInputValues = true
        encodeDefaults = true
        isLenient = true
    }

    @Provides
    @Singleton
    fun provideAccessTokenInterceptor(
        dataStore: DataStore<SolplyTokenData>
    ): AccessTokenInterceptor = AccessTokenInterceptor(dataStore)

    @Provides
    @Singleton
    fun provideLoggingInterceptor(buildConfigFieldProvider: BuildConfigFieldProvider): HttpLoggingInterceptor =
        HttpLoggingInterceptor().apply {
            level = if (buildConfigFieldProvider.get().isDebug) {
                HttpLoggingInterceptor.Level.BODY
            } else {
                HttpLoggingInterceptor.Level.NONE
            }
        }

    @Provides
    @Singleton
    fun provideErrorInterceptor(): Interceptor = Interceptor { chain ->
        val request = chain.request()
        try {
            val response = chain.proceed(request)
            if (!response.isSuccessful) {
                Log.e("NetworkModule", "Error response: ${response.code}")
            }
            response
        } catch (e: Exception) {
            Log.e("NetworkModule", "Network error", e)
            throw e
        }
    }

    @Provides
    @Singleton
    fun provideTokenRefreshAuthenticator(
        dataStore: DataStore<SolplyTokenData>,
        tokenRefreshService: TokenRefreshService,
        @ApplicationContext context: Context
    ): TokenRefreshAuthenticator = TokenRefreshAuthenticator(dataStore, tokenRefreshService, context)

    private fun createOkHttpClient(
        loggingInterceptor: HttpLoggingInterceptor,
        errorInterceptor: Interceptor,
        accessTokenInterceptor: AccessTokenInterceptor? = null,
        tokenRefreshAuthenticator: TokenRefreshAuthenticator? = null
    ): OkHttpClient =
        OkHttpClient.Builder()
            .retryOnConnectionFailure(true)
            .addInterceptor(loggingInterceptor)
            .addInterceptor(errorInterceptor)
            .apply {
                accessTokenInterceptor?.let { addInterceptor(it) }
                tokenRefreshAuthenticator?.let { authenticator(it) }
            }
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .build()

    @Provides
    @Singleton
    @AccessToken
    fun provideAuthOkHttpClient(
        loggingInterceptor: HttpLoggingInterceptor,
        errorInterceptor: Interceptor,
        accessTokenInterceptor: AccessTokenInterceptor,
        tokenRefreshAuthenticator: TokenRefreshAuthenticator
    ): OkHttpClient = createOkHttpClient(
        loggingInterceptor,
        errorInterceptor,
        accessTokenInterceptor,
        tokenRefreshAuthenticator
    )

    @Provides
    @Singleton
    @NoneAccessToken
    fun provideNoneAuthOkHttpClient(
        loggingInterceptor: HttpLoggingInterceptor,
        errorInterceptor: Interceptor
    ): OkHttpClient = createOkHttpClient(loggingInterceptor, errorInterceptor)

    private fun createRetrofit(
        okHttpClient: OkHttpClient,
        baseUrl: String,
        json: Json
    ): Retrofit = Retrofit.Builder()
        .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
        .baseUrl(baseUrl)
        .client(okHttpClient)
        .build()

    @Provides
    @Singleton
    @AccessToken
    fun provideAuthRetrofit(
        @AccessToken okHttpClient: OkHttpClient,
        buildConfigFieldProvider: BuildConfigFieldProvider,
        json: Json
    ): Retrofit = createRetrofit(okHttpClient, buildConfigFieldProvider.get().baseUrl, json)

    @Provides
    @Singleton
    @NoneAccessToken
    fun provideNoneAuthRetrofit(
        @NoneAccessToken okHttpClient: OkHttpClient,
        buildConfigFieldProvider: BuildConfigFieldProvider,
        json: Json
    ): Retrofit = createRetrofit(okHttpClient, buildConfigFieldProvider.get().baseUrl, json)
}
