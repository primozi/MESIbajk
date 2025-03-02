plugins {
    id("ui-plugin")
}

android {
    namespace = "software.ivancic.core.ui"

    compileOptions {
        isCoreLibraryDesugaringEnabled = true
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)

    coreLibraryDesugaring(libs.desugar)

    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.bundles.compose)

    api(libs.koin.annotations)
    ksp(libs.koin.annotations.ksp)

    api(libs.androidx.lifecycle.viewmodel)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}

// Compile time check
ksp {
    arg("KOIN_CONFIG_CHECK", "true")
}
