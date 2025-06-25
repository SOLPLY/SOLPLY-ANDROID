plugins {
    alias(libs.plugins.solply.data)
}

android {
    namespace = "com.teamsolply.solply.main"
}

dependencies {
    implementation(projects.core.datastore)
    implementation(projects.domain.main)
}
