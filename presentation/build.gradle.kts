plugins {
    id("com.mygomii.android.library")
    alias(libs.plugins.kotlin.compose)
    id("com.mygomii.presentation.deps")
}

android {
    namespace = "com.mygomii.booksearch.presentation"
    compileSdk = 36
    buildFeatures { compose = true }
}

dependencies {
    implementation(project(":domain"))
    implementation(project(":designsystem"))
}
