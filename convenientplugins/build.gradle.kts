plugins {
    kotlin("jvm") version "2.1.10"
    `java-gradle-plugin`
}

repositories {
    google()
    mavenCentral()
    gradlePluginPortal()
}

dependencies {
    compileOnly(libs.gradle)
    compileOnly(libs.kotlin.gradle.plugin)
}

gradlePlugin {
    plugins {
        register("uiPlugin") {
            id = "ui-plugin"
            implementationClass = "software.ivancic.plugins.UiPlugin"
        }

        register("domainPlugin") {
            id = "domain-plugin"
            implementationClass = "software.ivancic.plugins.DomainPlugin"
        }

        register("dataPlugin") {
            id = "data-plugin"
            implementationClass = "software.ivancic.plugins.DataPlugin"
        }
    }
}
