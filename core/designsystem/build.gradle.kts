plugins {
    alias(libs.plugins.solply.android.compose.library)
}

android {
    namespace = "com.teamsolply.solply.designsystem"
}

dependencies {
    implementation(projects.core.ui)
    implementation(projects.core.model)
}