plugins {
    alias(libs.plugins.solply.feature)
}

android {
    namespace = "com.teamsolply.solply.maps"
}

dependencies {
    implementation(projects.domain.maps)
    implementation(libs.bundles.naver.maps)
    implementation(libs.google.location)

    implementation(libs.lottie)
    implementation(libs.lottie.compose)
}
