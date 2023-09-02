plugins {
    id("com.android.application") version "8.1.1" apply false
    id("org.jetbrains.kotlin.android") version "1.9.0" apply false
    id("io.realm.kotlin") version "1.10.0" apply false
    id("com.google.dagger.hilt.android") version "2.48" apply false
}

buildscript {
    repositories {
        google()
    }
    dependencies {
        classpath("com.google.gms:google-services:4.3.15")
        classpath("androidx.navigation:navigation-safe-args-gradle-plugin:2.7.1")
    }
}
