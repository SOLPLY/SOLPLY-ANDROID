plugins {
    alias(libs.plugins.solply.local)
}

android {
    namespace = "com.teamsolply.solply.mypage"
}

dependencies {
    implementation(projects.core.model)
    implementation(projects.core.datastore)
    implementation(projects.data.mypage)
    implementation(libs.bundles.datastore)
}
