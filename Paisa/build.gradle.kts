buildscript {
    repositories {
        mavenCentral()
    }
    dependencies {
        classpath("io.realm:realm-gradle-plugin:10.15.1")
    }
}



plugins {
    alias(libs.plugins.androidApplication) apply false
}