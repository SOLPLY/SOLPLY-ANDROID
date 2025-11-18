import com.teamsolply.solply.convention.extension.implementation

plugins {
    alias(libs.plugins.solply.feature)
}

android {
    namespace = "com.teamsolply.solply.oauth"
}

dependencies {
    implementation(libs.kakao.login)
    implementation(libs.google.id)
    implementation(libs.credentials.play.auth)
    implementation(libs.credentials)
    implementation(projects.domain.oauth)
}
