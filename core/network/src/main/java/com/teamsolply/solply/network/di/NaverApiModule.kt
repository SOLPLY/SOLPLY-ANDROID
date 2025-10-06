package com.teamsolply.solply.network.di

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.teamsolply.solply.common.buildconfig.BuildConfigFieldProvider
import com.teamsolply.solply.network.service.NaverLocalSearchService
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
import javax.inject.Qualifier
import javax.inject.Singleton

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class NaverApi

@Module
@InstallIn(SingletonComponent::class)
object NaverApiModule {

    @Provides
    @Singleton
    @NaverApi
    fun provideNaverApiInterceptor(
        buildConfigFieldProvider: BuildConfigFieldProvider
    ): Interceptor = Interceptor { chain ->
        val originalRequest = chain.request()
        val newRequest = originalRequest.newBuilder()
            .addHeader("X-Naver-Client-Id", buildConfigFieldProvider.get().naverDevelopersClientId)
            .addHeader("X-Naver-Client-Secret", buildConfigFieldProvider.get().naverDevelopersClientSecret)
            .build()
        chain.proceed(newRequest)
    }

    @Provides
    @Singleton
    @NaverApi
    fun provideNaverOkHttpClient(
        loggingInterceptor: HttpLoggingInterceptor,
        @NaverApi naverApiInterceptor: Interceptor
    ): OkHttpClient = OkHttpClient.Builder()
        .retryOnConnectionFailure(true)
        .addInterceptor(loggingInterceptor.apply {
            level = HttpLoggingInterceptor.Level.HEADERS
        })
        .addInterceptor(naverApiInterceptor)
        .connectTimeout(30, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .writeTimeout(30, TimeUnit.SECONDS)
        .build()

    @Provides
    @Singleton
    @NaverApi
    fun provideNaverRetrofit(
        @NaverApi okHttpClient: OkHttpClient,
        json: Json
    ): Retrofit = Retrofit.Builder()
        .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
        .baseUrl("https://openapi.naver.com/")
        .client(okHttpClient)
        .build()

    @Provides
    @Singleton
    fun provideNaverGeocodingService(
        @NaverApi retrofit: Retrofit
    ): NaverLocalSearchService = retrofit.create(NaverLocalSearchService::class.java)
}