plugins {
    id("ui-plugin")
}

android {
    namespace = "software.ivancic.bikes.ui"
}

dependencies {

    api(project(":core:ui"))
    implementation(project(":bikes:domain"))

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}
