plugins {
    id("com.mygomii.android.library")
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.ksp)
    id("com.mygomii.data.deps")
}

android {
    namespace = "com.mygomii.booksearch.data"
    compileSdk = 36
}

dependencies {
    implementation(project(":domain"))
}
