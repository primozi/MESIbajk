plugins {
    id("domain-plugin")
}

dependencies {
    api(project(":core:domain"))

    api(libs.koin.annotations)
    ksp(libs.koin.annotations.ksp)

//    implementation(libs.timber)
}
