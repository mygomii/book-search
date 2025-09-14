plugins {
    id("com.mygomii.android.library")
    alias(libs.plugins.kotlin.compose)
}

android {
    namespace = "com.mygomii.booksearch.presentation"
    compileSdk = 36
    buildFeatures { compose = true }
}

dependencies {
    implementation(project(":domain"))

    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.compose.material.icons.extended)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.coil.compose)
    implementation(libs.kotlinx.coroutines.android)

    debugImplementation(libs.androidx.compose.ui.tooling)
}
