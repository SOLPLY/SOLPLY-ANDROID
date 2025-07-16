plugins {
    alias(libs.plugins.solply.feature)
}

android {
    namespace = "com.teamsolply.solply.onboarding"
}

dependencies {
    implementation(projects.domain.onboarding)
    implementation(libs.lottie)
    implementation(libs.lottie.compose)
    implementation(libs.ui.foundation)
}
