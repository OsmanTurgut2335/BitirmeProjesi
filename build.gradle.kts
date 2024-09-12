

// Top-level build file where you can add configuration options common to all sub-projects/modules.

buildscript {
    repositories {
        google()
        mavenCentral() // Add this if it's not present
    }
    dependencies {
        // Other plugins...
        classpath ("com.google.dagger:hilt-android-gradle-plugin:2.52") // Use the latest Hilt plugin version
    }
}
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.jetbrains.kotlin.android) apply false
    id("com.google.gms.google-services") version "4.4.2" apply false

    id("com.google.dagger.hilt.android") version "2.51.1" apply false
}
