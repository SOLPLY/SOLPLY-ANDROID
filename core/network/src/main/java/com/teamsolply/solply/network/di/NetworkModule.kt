package com.teamsolply.solply.network.di

import android.util.Log
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.teamsolply.solply.common.buildconfig.BuildConfigFieldProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
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
                // 에러 응답 처리
                Log.e("NetworkModule", "Error response: ${response.code}")
            }
            response
        } catch (e: Exception) {
            Log.e("NetworkModule", "Network error", e)
            throw e
        }
    }

    private fun createOkHttpClient(
        loggingInterceptor: HttpLoggingInterceptor,
        errorInterceptor: Interceptor
    ): OkHttpClient =
        OkHttpClient.Builder()
            .retryOnConnectionFailure(true)
            .addInterceptor(loggingInterceptor)
            .addInterceptor(errorInterceptor)
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .build()

    @Provides
    @Singleton
    @Auth
    fun provideAuthOkHttpClient(
        loggingInterceptor: HttpLoggingInterceptor,
        errorInterceptor: Interceptor
    ): OkHttpClient = createOkHttpClient(loggingInterceptor, errorInterceptor)

    @Provides
    @Singleton
    @NoneAuth
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
    @Auth
    fun provideAuthRetrofit(
        @Auth okHttpClient: OkHttpClient,
        buildConfigFieldProvider: BuildConfigFieldProvider,
        json: Json
    ): Retrofit = createRetrofit(okHttpClient, buildConfigFieldProvider.get().baseUrl, json)

    @Provides
    @Singleton
    @NoneAuth
    fun provideNoneAuthRetrofit(
        @NoneAuth okHttpClient: OkHttpClient,
        buildConfigFieldProvider: BuildConfigFieldProvider,
        json: Json
    ): Retrofit = createRetrofit(okHttpClient, buildConfigFieldProvider.get().baseUrl, json)
}
