plugins {
    alias(libs.plugins.solply.data)
}

android {
    namespace = "com.teamsolply.solply"
}

dependencies {
    implementation(projects.core.model)
    implementation(projects.core.datastore)
    implementation(projects.data)
    implementation(libs.bundles.datastore)
}
