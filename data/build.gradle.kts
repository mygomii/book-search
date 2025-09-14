plugins {
    id("com.mygomii.android.library")
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.ksp)
}

android {
    namespace = "com.mygomii.booksearch.data"
    compileSdk = 36
}

dependencies {
    implementation(project(":domain"))

    implementation(libs.kotlinx.coroutines.core)

    api(libs.ktor.client.android)
    api(libs.ktor.client.content.negotiation)
    api(libs.ktor.serialization.kotlinx.json)
    api(libs.ktor.client.logging)

    implementation(libs.androidx.room.runtime)
    implementation(libs.androidx.room.ktx)
    ksp(libs.androidx.room.compiler)
}
