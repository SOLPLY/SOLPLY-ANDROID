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
            storeFile = file("${project.projectDir}/debug.keystore")
            storePassword = "android"
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
    implementation(projects.domain.mypage)
    implementation(projects.domain.maps)
    implementation(projects.data.main)
    implementation(projects.data.oauth)
    implementation(projects.data.onboarding)
    implementation(projects.data.place)
    implementation(projects.data.course)
    implementation(projects.data.mypage)
    implementation(projects.data.maps)
    implementation(projects.local.main)
    implementation(projects.local.oauth)
    implementation(projects.local.place)
    implementation(projects.remote.oauth)
    implementation(projects.remote.onboarding)
    implementation(projects.remote.place)
    implementation(projects.remote.course)
    implementation(projects.remote.mypage)
    implementation(projects.remote.maps)
    implementation(projects.feature.main)
    implementation(projects.feature.oauth)
    implementation(projects.feature.onboarding)
    implementation(projects.feature.place)
    implementation(projects.feature.course)
    implementation(projects.feature.mypage)
    implementation(projects.feature.maps)
    implementation(libs.kakao.login)
}
