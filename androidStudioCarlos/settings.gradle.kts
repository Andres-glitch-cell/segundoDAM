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
plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "1.0.0"
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "Android Studio Carlos"
include(":UD02_Layouts_y_TextView")
include(":UD03_HolaMundo_y_Intents")
include(":myApplicationMadedByAI")
include(":UD02_Calculadora")
include(":UD02_EditText")
include(":UD02_Dialogs")
include(":UD02_Notificaciones")
