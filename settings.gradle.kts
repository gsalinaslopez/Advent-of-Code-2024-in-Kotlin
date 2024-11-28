rootProject.name = "Advent-of-Code-2024-in-Kotlin"

pluginManagement {
    repositories {
        mavenCentral()
        google()
        gradlePluginPortal()
        maven("https://maven.pkg.jetbrains.space/public/p/amper/amper")
    }
}

plugins {
    id("org.jetbrains.amper.settings.plugin").version("0.1.0")
}
