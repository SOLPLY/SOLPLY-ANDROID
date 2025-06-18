plugins {
    alias(libs.plugins.solply.feature)
}

android {
    namespace = "com.teamsolply.solply.main"
}

dependencies {
    implementation(projects.feature.home)
}