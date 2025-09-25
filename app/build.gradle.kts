import com.teamsolply.solply.convention.extension.implementation

plugins {
    alias(libs.plugins.solply.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.solply.android.hilt)
}

android {
    namespace = "com.teamsolply.solply"

    signingConfigs {
        getByName("debug") {
            keyAlias = "androiddebugkey"
            keyPassword = "android"
            storeFile = file("debug.keystore")
        }
    }

    buildTypes {
        debug {
            signingConfig = signingConfigs.getByName("debug")
        }
        release {
            signingConfig = signingConfigs.getByName("debug")
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
}

dependencies {
    implementation(projects.core.common)
    implementation(projects.core.designsystem)
    implementation(projects.core.buildconfig)
    implementation(projects.core.datastore)
    implementation(projects.core.network)
    implementation(projects.core.navigation)
    implementation(projects.domain.main)
    implementation(projects.domain.oauth)
    implementation(projects.domain.onboarding)
    implementation(projects.domain.place)
    implementation(projects.domain.course)
    implementation(projects.domain.collection)
    implementation(projects.domain.maps)
    implementation(projects.domain.mypage)
    implementation(projects.data.main)
    implementation(projects.data.oauth)
    implementation(projects.data.onboarding)
    implementation(projects.data.place)
    implementation(projects.data.course)
    implementation(projects.data.collection)
    implementation(projects.data.maps)
    implementation(projects.data.mypage)
    implementation(projects.local.main)
    implementation(projects.local.oauth)
    implementation(projects.local.place)
    implementation(projects.remote.oauth)
    implementation(projects.remote.onboarding)
    implementation(projects.remote.place)
    implementation(projects.remote.course)
    implementation(projects.remote.collection)
    implementation(projects.remote.maps)
    implementation(projects.remote.mypage)
    implementation(projects.feature.main)
    implementation(projects.feature.oauth)
    implementation(projects.feature.onboarding)
    implementation(projects.feature.place)
    implementation(projects.feature.course)
    implementation(projects.feature.collection)
    implementation(projects.feature.maps)
    implementation(projects.feature.mypage)
    implementation(projects.feature.search)
    implementation(libs.kakao.login)
}
