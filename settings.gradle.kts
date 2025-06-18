enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")
pluginManagement {
    includeBuild("build-logic")
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "SOLPLY"
include(":app")
include(":core:buildconfig")
include(":core:common")
include(":core:designsystem")
include(":core:model")
include(":core:ui")
include(":core:network")
include(":core:datastore")
include(":core:navigation")
include(":domain")
include(":data")
include(":local")
include(":remote")
include(":feature:main")
include(":feature:home")
