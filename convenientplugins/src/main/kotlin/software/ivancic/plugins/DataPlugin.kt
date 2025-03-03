package software.ivancic.plugins

import com.android.build.gradle.LibraryExtension
import org.gradle.api.JavaVersion
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.dsl.KotlinAndroidProjectExtension

/**
 * This could be further optimized and joined with [UiPlugin]. Also, some of it should be made so
 * that :app module could reuse this as well.
 *
 * There are some dependencies that are common to the modules as well, that
 * could/should be included here
 */
class DataPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        target.plugins.apply("com.android.library") // id(libs.plugins.android.library)
        target.plugins.apply("org.jetbrains.kotlin.android") // id(libs.plugins.kotlin.android)
        target.plugins.apply("com.google.devtools.ksp") // id(libs.plugins.ksp)

        target.applyAndroid()
        target.applyKotlin()
    }

    private fun Project.applyAndroid() {
        val android = extensions.findByType(LibraryExtension::class.java)!!

        with(android) {
            compileSdk = 35

            defaultConfig {
                minSdk = 21

                testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
                consumerProguardFiles("consumer-rules.pro")
            }

            buildTypes {
                release {
                    isMinifyEnabled = false
                    proguardFiles(
                        getDefaultProguardFile("proguard-android-optimize.txt"),
                        "proguard-rules.pro"
                    )
                }
            }
            compileOptions {
                sourceCompatibility = JavaVersion.VERSION_21
                targetCompatibility = JavaVersion.VERSION_21
            }
        }
    }

    private fun Project.applyKotlin() {
        val kotlin = extensions.findByType(KotlinAndroidProjectExtension::class.java)!!
        with(kotlin) {
            compilerOptions {
                jvmTarget.set(JvmTarget.JVM_21)
            }
        }
    }
}
