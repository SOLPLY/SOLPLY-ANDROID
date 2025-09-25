plugins {
    alias(libs.plugins.solply.feature)
}
android {
    namespace = "com.teamsolply.solply.mypage"
}

dependencies {
    implementation(projects.domain.mypage)
}
