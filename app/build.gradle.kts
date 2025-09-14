import java.util.Properties

plugins {
    id("com.mygomii.android.application")
    alias(libs.plugins.ksp)
    id("com.mygomii.app.deps")
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
}
