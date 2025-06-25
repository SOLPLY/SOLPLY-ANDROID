plugins {
    alias(libs.plugins.solply.data)
}

android {
    namespace = "com.teamsolply.solply.oauth"
}

dependencies {
    implementation(projects.core.datastore)
    implementation(projects.domain.oauth)
}