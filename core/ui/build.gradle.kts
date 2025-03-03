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

    debugImplementation(libs.androidx.ui.test.manifest)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.ui.test.junit4)
    androidTestImplementation(libs.androidx.test.junit)
    androidTestImplementation(libs.androidx.test.espresso.core)
}

// Compile time check
ksp {
    arg("KOIN_CONFIG_CHECK", "true")
}
