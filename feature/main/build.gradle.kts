plugins {
    alias(libs.plugins.solply.feature)
}

android {
    namespace = "com.teamsolply.solply.main"
}

dependencies {
    implementation(projects.feature.oauth)
    implementation(projects.feature.onboarding)
    implementation(projects.feature.place)
    implementation(projects.feature.course)
    implementation(projects.feature.maps)
    implementation(projects.feature.mypage)
}
