plugins {
    alias(libs.plugins.solply.feature)
}

android {
    namespace = "com.teamsolply.solply.search"
}

dependencies {
    implementation(projects.core.designsystem)
    implementation(projects.core.model)
    implementation(projects.core.ui)
    implementation(projects.domain.place)
    implementation(projects.domain.search)
}
