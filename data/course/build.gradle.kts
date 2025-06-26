plugins {
    alias(libs.plugins.solply.data)
}

android {
    namespace = "com.teamsolply.solply.course"
}

dependencies {
    implementation(projects.domain.course)
}
