plugins {
    alias(libs.plugins.solply.data)
}

android {
    namespace = "com.teamsolply.solply.collection"
}

dependencies {
    implementation(projects.domain.collection)
}
