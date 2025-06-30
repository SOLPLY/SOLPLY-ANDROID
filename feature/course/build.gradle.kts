plugins {
    alias(libs.plugins.solply.feature)
}

android {
    namespace = "com.teamsolply.solply.course"
}

dependencies {
    implementation(projects.domain.course)
}
