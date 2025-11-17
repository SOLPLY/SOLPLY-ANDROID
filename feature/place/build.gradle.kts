plugins {
    alias(libs.plugins.solply.feature)
}

android {
    namespace = "com.teamsolply.solply.place"
}

dependencies {
    implementation(projects.domain.place)
    implementation(projects.feature.search)
    implementation(projects.feature.course)
    implementation(libs.accompanist.permissions)
}
