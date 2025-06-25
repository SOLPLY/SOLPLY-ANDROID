plugins {
    alias(libs.plugins.solply.data)
}

android {
    namespace = "com.teamsolply.solply.oauth"
}

dependencies {
    implementation(projects.core.model)
    implementation(projects.core.datastore)
    implementation(projects.data.oauth)
    implementation(libs.bundles.datastore)
}
