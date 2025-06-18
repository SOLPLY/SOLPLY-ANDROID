plugins {
    alias(libs.plugins.solply.android.library)
    alias(libs.plugins.solply.android.hilt)
    alias(libs.plugins.solply.plugin.test)
    alias(libs.plugins.solply.plugin.build.config)
}

android {
    namespace = "com.teamsolply.solply.buildconfig"
}

dependencies {
    implementation(projects.core.common)
}
