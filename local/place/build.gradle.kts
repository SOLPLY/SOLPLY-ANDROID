plugins {
    alias(libs.plugins.solply.data)
}

android {
    namespace = "com.teamsolply.solply.place"
}

dependencies {
    implementation(projects.core.model)
    implementation(projects.core.datastore)
    implementation(projects.data.place)
    implementation(libs.bundles.datastore)
}
