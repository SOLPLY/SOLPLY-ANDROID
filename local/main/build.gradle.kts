plugins {
    alias(libs.plugins.solply.data)
}

android {
    namespace = "com.teamsolply.solply.main"
}

dependencies {
    implementation(projects.core.model)
    implementation(projects.core.datastore)
    implementation(projects.data.main)
    implementation(libs.bundles.datastore)
}
