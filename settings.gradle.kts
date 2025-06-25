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
        maven(url = "https://devrepo.kakao.com/nexus/content/groups/public/")
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
include(":domain:oauth")
include(":domain:place")
include(":data:oauth")
include(":data:place")
include(":local:oauth")
include(":remote")
include(":feature:main")
include(":feature:oauth")
include(":feature:onboarding")
include(":feature:place")
include(":feature:course")
include(":feature:maps")
include(":feature:mypage")
