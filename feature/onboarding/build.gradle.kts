plugins {
    alias(libs.plugins.solply.feature)
}

android {
    namespace = "com.teamsolply.solply.onboarding"
}

dependencies {
    implementation(projects.domain.onboarding)
    implementation("androidx.compose.foundation:foundation:1.6.1")
    implementation("com.airbnb.android:lottie-compose:6.1.0")
}
