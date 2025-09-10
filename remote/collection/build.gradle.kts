plugins {
    alias(libs.plugins.solply.remote)
}

android {
    namespace = "com.teamsolply.solply.collection"
}

dependencies {
    implementation(projects.core.network)
    implementation(projects.core.model)
    implementation(projects.data.collection)
}
