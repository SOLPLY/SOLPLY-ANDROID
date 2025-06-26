plugins {
    alias(libs.plugins.solply.data)
}

android {
    namespace = "com.teamsolply.solply.maps"
}

dependencies {
    implementation(projects.domain.maps)
}
