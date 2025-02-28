plugins {
    id("data-plugin")
}

android {
    namespace = "software.ivancic.bikes.data"
}

dependencies {
    api(project(":core:data"))
    api(project(":bikes:domain"))

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}
