plugins {
    id("domain-plugin")
}

dependencies {
    api(libs.kotlin.coroutines.android)

    api(platform(libs.koin.bom))
    api(libs.koin.compose.viewmodel.navigation)

    api(libs.koin.annotations)
    ksp(libs.koin.annotations.ksp)
}

// Compile time check
ksp {
    arg("KOIN_CONFIG_CHECK", "true")
}
