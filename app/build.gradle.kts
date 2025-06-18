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

    implementation(projects.data)
    implementation(projects.local)
    implementation(projects.remote)
    implementation(projects.domain)
    implementation(projects.feature.main)
}
