
plugins {
    alias(libs.plugins.solply.android.library)
    alias(libs.plugins.solply.android.hilt)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.solply.plugin.test)
}

android {
    namespace = "com.teamsolply.solply.datastore"
}

dependencies {
    implementation(projects.core.common)
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.bundles.datastore)
}
