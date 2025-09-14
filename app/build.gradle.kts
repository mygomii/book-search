import java.util.Properties

plugins {
    id("com.mygomii.android.application")
}

android {
    namespace = "com.mygomii.book_search"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.mygomii.book_search"
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    buildFeatures { buildConfig = true }
}

val kakaoKey: String = run {
    val props = Properties()
    val f = rootProject.file("local.properties")
    if (f.exists()) f.inputStream().use(props::load)
    props.getProperty("KAKAO_API_KEY") ?: System.getenv("KAKAO_API_KEY") ?: ""
}

android.defaultConfig {
    buildConfigField("String", "KAKAO_API_KEY", "\"$kakaoKey\"")
}

dependencies {

    implementation(project(":domain"))
    implementation(project(":data"))
    implementation(project(":presentation"))

    implementation(libs.androidx.room.runtime)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.compose.material3)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)
    debugImplementation(libs.androidx.compose.ui.tooling)
    debugImplementation(libs.androidx.compose.ui.test.manifest)
}
