plugins {
    alias(libs.plugins.solply.android.library)
    alias(libs.plugins.solply.android.hilt)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.solply.plugin.test)
}

android {
    namespace = "com.teamsolply.solply.network"
}

dependencies {
    implementation(projects.core.datastore)
    implementation(libs.bundles.datastore)
    implementation(projects.core.common)
    implementation(projects.core.model)
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.retrofit.core)
    implementation(libs.retrofit.kotlin.serialization)
    implementation(libs.okhttp.logging)
    implementation(libs.process.phoenix)
}
