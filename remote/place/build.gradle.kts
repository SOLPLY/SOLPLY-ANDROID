plugins {
    alias(libs.plugins.solply.remote)
}

android {
    namespace = "com.teamsolply.solply.place"
}

dependencies {
    implementation(projects.core.network)
    implementation(projects.core.model)
    implementation(projects.data.place)
}