plugins {
    alias(libs.plugins.solply.data)
}

android {
    namespace = "com.teamsolply.solply.onboarding"
}

dependencies {
    implementation(projects.core.network)
    implementation(projects.core.model)
    implementation(projects.data.onboarding)
}