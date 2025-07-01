plugins {
    alias(libs.plugins.solply.feature)
}

android {
    namespace = "com.teamsolply.solply.maps"
}

dependencies {
    implementation(projects.domain.maps)
    implementation(libs.bundles.naver.maps)
}
