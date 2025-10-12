plugins {
    alias(libs.plugins.solply.java.library)
    alias(libs.plugins.kotlin.serialization)
}

dependencies {
    implementation(projects.core.model)
    implementation(libs.bundles.coroutine)
    implementation(libs.kotlinx.serialization.json)
}
