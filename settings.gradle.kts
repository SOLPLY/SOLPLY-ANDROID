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
        maven(url = "https://repository.map.naver.com/archive/maven")
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
include(":domain:main")
include(":domain:oauth")
include(":domain:onboarding")
include(":domain:place")
include(":domain:course")
include(":domain:collection")
include(":domain:maps")
include(":data:main")
include(":data:oauth")
include(":data:onboarding")
include(":data:place")
include(":data:course")
include(":data:collection")
include(":data:maps")
include(":local:main")
include(":local:oauth")
include(":local:place")
include(":remote:oauth")
include(":remote:onboarding")
include(":remote:place")
include(":remote:course")
include(":remote:collection")
include(":remote:maps")
include(":feature:main")
include(":feature:oauth")
include(":feature:onboarding")
include(":feature:place")
include(":feature:course")
include(":feature:collection")
include(":feature:maps")
include(":feature:mypage")
include(":domain:mypage")
include(":data:mypage")
include(":remote:mypage")
