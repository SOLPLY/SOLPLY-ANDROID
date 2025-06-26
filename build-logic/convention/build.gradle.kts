plugins {
    `kotlin-dsl`
}

group = "com.teamsolply.solply.convention"

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

dependencies {
    compileOnly(libs.android.gradle.plugin)
    compileOnly(libs.kotlin.gradle.plugin)
    compileOnly(libs.ksp.gradle.plugin)
    compileOnly(libs.compose.compiler.extension)
}

gradlePlugin {
    plugins {
        register("androidApplication") {
            id = "solply.android.application"
            implementationClass = "AndroidApplicationPlugin"
        }

        register("androidLibrary") {
            id = "solply.android.library"
            implementationClass = "AndroidLibraryPlugin"
        }

        register("androidComposeLibrary") {
            id = "solply.android.compose.library"
            implementationClass = "AndroidComposeLibraryPlugin"
        }

        register("androidHilt") {
            id = "solply.android.hilt"
            implementationClass = "HiltPlugin"
        }

        register("javaLibrary") {
            id = "solply.java.library"
            implementationClass = "JavaLibraryPlugin"
        }

        register("buildConfig") {
            id = "solply.plugin.build.config"
            implementationClass = "BuildConfigPlugin"
        }

        register("androidTest") {
            id = "solply.plugin.android.test"
            implementationClass = "AndroidTestPlugin"
        }

        register("unitTest") {
            id = "solply.plugin.test"
            implementationClass = "UnitTestPlugin"
        }

        register("solplyFeature") {
            id = "solply.feature"
            implementationClass = "SolplyFeaturePlugin"
        }

        register("solplyData") {
            id = "solply.data"
            implementationClass = "SolplyDataPlugin"
        }

        register("solplyLocal") {
            id = "solply.local"
            implementationClass = "SolplyLocalPlugin"
        }

        register("solplyRemote") {
            id = "solply.remote"
            implementationClass = "SolplyRemotePlugin"
        }
    }
}