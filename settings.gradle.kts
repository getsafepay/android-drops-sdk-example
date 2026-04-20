pluginManagement {
    repositories {
        google()
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

rootProject.name = "android-drops-sdk-example"
include(":app")
include(":android-drops-sdk")
project(":android-drops-sdk").projectDir = file("../android-drops-sdk/sdk")
