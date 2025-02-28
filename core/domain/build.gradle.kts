plugins {
    id("domain-plugin")
}

dependencies {
    api(libs.kotlin.coroutines.android)

    api(platform(libs.koin.bom))
    api(libs.koin.android)

    api(libs.koin.annotations)
    implementation(libs.koin.annotations.ksp)
}

// Compile time check
ksp {
    arg("KOIN_CONFIG_CHECK", "true")
}
