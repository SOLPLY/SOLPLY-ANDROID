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
    implementation(projects.feature.serach)
    implementation(projects.feature.course)
    implementation(projects.feature.maps)
    implementation(projects.feature.collection)
    implementation(libs.androidx.splashscreen)
    implementation(libs.lottie)
    implementation(libs.lottie.compose)
    implementation(libs.material3.compose)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.google.material)
}
