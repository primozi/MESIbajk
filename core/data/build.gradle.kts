plugins {
    id("data-plugin")
}

android {
    namespace = "software.ivancic.core.data"
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)

    implementation(project(":core:domain"))

    api(libs.koin.annotations)
    ksp(libs.koin.annotations.ksp)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}

// Compile time check
ksp {
    arg("KOIN_CONFIG_CHECK", "true")
}
