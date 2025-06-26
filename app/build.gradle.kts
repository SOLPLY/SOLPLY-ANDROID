plugins {
    alias(libs.plugins.solply.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.solply.android.hilt)
}

android {
    namespace = "com.teamsolply.solply"
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
    implementation(projects.data.main)
    implementation(projects.data.oauth)
    implementation(projects.data.onboarding)
    implementation(projects.data.place)
    implementation(projects.local.main)
    implementation(projects.local.oauth)
    implementation(projects.local.place)
    implementation(projects.remote.onboarding)
    implementation(projects.feature.main)
    implementation(projects.feature.oauth)
    implementation(projects.feature.onboarding)
    implementation(projects.feature.place)
    implementation(projects.feature.course)
    implementation(projects.feature.mypage)
    implementation(projects.feature.maps)
    implementation(libs.kakao.login)
}
