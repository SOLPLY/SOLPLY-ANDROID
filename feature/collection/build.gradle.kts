plugins {
    alias(libs.plugins.solply.feature)
}

android {
    namespace = "com.teamsolply.solply.collection"
}

dependencies {
    implementation(projects.domain.collection)
    implementation(projects.core.designsystem)
}
