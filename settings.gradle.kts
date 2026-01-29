pluginManagement {
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

rootProject.name = "QuilterAndroidTechTest"
enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")
include(":app")
include(":core")
include(":feature")
include(":core:data")
include(":core:model")
include(":core:network")
include(":core:common")
include(":core:domain")
include(":feature:book-list")
