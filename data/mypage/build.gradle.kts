plugins {
    alias(libs.plugins.solply.data)
}

android {
    namespace = "com.teamsolply.solply.mypage"
}

dependencies {
    implementation(projects.domain.mypage)
}
