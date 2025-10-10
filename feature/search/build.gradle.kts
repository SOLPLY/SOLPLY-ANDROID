plugins {
    alias(libs.plugins.solply.feature)
}

android {
    namespace = "com.teamsolply.solply.search"
}

dependencies {
    implementation(projects.domain.search)
    implementation(projects.domain.maps)
    implementation(libs.lottie)
    implementation(libs.lottie.compose)
}
