plugins {
    id("com.mygomii.android.library")
    alias(libs.plugins.kotlin.compose)
    id("com.mygomii.designsystem.deps")
}

android {
    namespace = "com.mygomii.booksearch.designsystem"
    compileSdk = 36
    buildFeatures { compose = true }
}
