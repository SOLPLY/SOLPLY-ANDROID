plugins {
    alias(libs.plugins.solply.data)
}

android {
    namespace = "com.teamsolply.solply.mypage"
}

dependencies {
    implementation(projects.core.datastore)
    implementation(projects.domain.mypage)
}
