plugins {
    id("data-plugin")
    alias(libs.plugins.room)
}

room {
    schemaDirectory("$projectDir/src/main/schemas")
}

android {
    namespace = "software.ivancic.bikes.data"
}

dependencies {
    api(project(":core:data"))
    api(project(":bikes:domain"))

    ksp(libs.room.compiler)
    implementation(libs.bundles.room)

    implementation(platform(libs.koin.bom))
    implementation(libs.koin.android)
    implementation(libs.koin.compose.viewmodel.navigation)

    ksp(libs.koin.annotations.ksp)

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)

    implementation(libs.fliptables)

    testImplementation(libs.junit)

    androidTestImplementation(libs.androidx.test.junit)
    androidTestImplementation(libs.androidx.test.core)
    androidTestImplementation(libs.androidx.test.runner)
    androidTestImplementation(libs.kotlin.coroutines.test)
}
