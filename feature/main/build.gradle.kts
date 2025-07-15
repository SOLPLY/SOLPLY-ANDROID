plugins {
    alias(libs.plugins.solply.feature)
}

android {
    namespace = "com.teamsolply.solply.main"
}

dependencies {
    implementation(projects.domain.main)
    implementation(projects.feature.oauth)
    implementation(projects.feature.onboarding)
    implementation(projects.feature.place)
    implementation(projects.feature.course)
    implementation(projects.feature.maps)
    implementation(projects.feature.mypage)
    implementation("androidx.core:core-splashscreen:1.0.0")
    implementation ("com.airbnb.android:lottie-compose:6.1.0")
}
