plugins {
    id("ui-plugin")
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "software.ivancic.bikes.ui"
}

dependencies {

    api(project(":core:ui"))
    implementation(project(":bikes:domain"))

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)

    implementation(libs.kotlin.serialization.json)

    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.bundles.compose)

    implementation(platform(libs.koin.bom))
    implementation(libs.bundles.koin)

    api(libs.koin.annotations)
    ksp(libs.koin.annotations.ksp)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.test.junit)
    androidTestImplementation(libs.androidx.test.espresso.core)
}
