plugins {
    alias(libs.plugins.solply.data)
}

android {
    namespace = "com.teamsolply.solply.place"
}

dependencies {
    implementation(projects.core.datastore)
    implementation(projects.domain.place)
}
