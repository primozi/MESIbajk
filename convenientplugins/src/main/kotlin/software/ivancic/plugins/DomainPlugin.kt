package software.ivancic.plugins

import org.gradle.api.JavaVersion
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.plugins.JavaPluginExtension
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.dsl.KotlinJvmProjectExtension

class DomainPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        target.plugins.apply("java-library")
        target.plugins.apply("org.jetbrains.kotlin.jvm")
        target.plugins.apply("com.google.devtools.ksp")

        target.applyKotlin()
        target.applyJava()
    }

    private fun Project.applyKotlin() {
        val kotlin = extensions.findByType(KotlinJvmProjectExtension::class.java)!!
        with(kotlin) {
            compilerOptions {
                jvmTarget.set(JvmTarget.JVM_21)
            }
        }
    }

    private fun Project.applyJava() {
        val java = extensions.findByType(JavaPluginExtension::class.java)!!
        with(java) {
            sourceCompatibility = JavaVersion.VERSION_21
            targetCompatibility = JavaVersion.VERSION_21
        }
    }
}
