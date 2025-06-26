plugins {
    alias(libs.plugins.solply.java.library)
}

dependencies {
    implementation(projects.core.model)
    implementation(libs.bundles.coroutine)
}
