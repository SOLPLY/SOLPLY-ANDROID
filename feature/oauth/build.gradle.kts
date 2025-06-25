plugins {
    alias(libs.plugins.solply.feature)
}

android {
    namespace = "com.teamsolply.solply.oauth"
}

dependencies {
    implementation(libs.kakao.login)
    implementation(projects.domain.oauth)
}
