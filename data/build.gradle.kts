plugins {
    alias(libs.plugins.solply.data)
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "com.teamsolply.solply"
}

dependencies {
    implementation(projects.core.network)
    implementation(projects.core.model)
    implementation(projects.core.datastore)
    implementation(projects.domain)
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.retrofit.core)
    implementation(libs.retrofit.kotlin.serialization)
    implementation(libs.okhttp.logging)
}
