plugins {
    alias(libs.plugins.solply.android.compose.library)
}

android {
    namespace = "com.teamsolply.solply.ui"
}

dependencies {
    implementation(libs.bundles.coil)
}
