plugins {
    alias(libs.plugins.solply.android.library)

}

android {
    namespace = "com.teamsolply.solply.search"
}

dependencies {
    implementation(projects.domain.search)
}