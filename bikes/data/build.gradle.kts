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
    implementation(libs.koin.android.base)
    implementation(libs.koin.android)

    ksp(libs.koin.annotations.ksp)

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}
