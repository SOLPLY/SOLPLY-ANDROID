package com.teamsolply.solply.convention

import com.android.build.api.dsl.CommonExtension
import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties
import org.gradle.api.Project

internal fun Project.configureBuildConfig(
    commonExtension: CommonExtension<*, *, *, *, *, *>,
) {
    commonExtension.apply {
        defaultConfig {
            buildConfigField(
                "String",
                "BASE_URL",
                gradleLocalProperties(rootDir, providers).getProperty("base.url")
            )
            buildConfigField(
                "String",
                "KAKAO_NATIVE_KEY",
                gradleLocalProperties(rootDir, providers).getProperty("kakao.native.key")
            )
            buildConfigField(
                "String",
                "NAVER_CLIENT_ID",
                gradleLocalProperties(rootDir, providers).getProperty("naver.client.id")
            )
            buildConfigField(
                "String",
                "NAVER_DEVELOPERS_CLIENT_ID",
                gradleLocalProperties(rootDir, providers).getProperty("naver.developers.client.id")
            )
            buildConfigField(
                "String",
                "NAVER_DEVELOPERS_CLIENT_SECRET",
                gradleLocalProperties(rootDir, providers).getProperty("naver.developers.client.secret")
            )
            buildConfigField(
                "String",
                "GOOGLE_WEB_CLIENT_ID",
                gradleLocalProperties(rootDir, providers).getProperty("google.web.client.id")
            )
        }

        buildFeatures {
            buildConfig = true
        }
    }
}