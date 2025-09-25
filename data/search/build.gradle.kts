plugins {
    alias(libs.plugins.solply.data)
}

android {
    namespace = "com.teamsolply.solply.search"
}

dependencies {
    implementation(projects.core.datastore)
    implementation(projects.domain.search)
}
