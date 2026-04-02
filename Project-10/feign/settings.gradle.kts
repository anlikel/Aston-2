rootProject.name = "feign"
include("input", "output")

pluginManagement {
    repositories {
        mavenCentral()
        gradlePluginPortal()
    }

    plugins {
        id("java")
        id("org.springframework.boot") version "3.4.3"
        id("io.spring.dependency-management") version "1.1.7"
    }
}