plugins {
    `kotlin-dsl`
}

group = "com.mygomii.buildlogic"

gradlePlugin {
    plugins {
        register("androidApplicationConvention") {
            id = "com.mygomii.android.application"
            implementationClass = "AndroidApplicationConventionPlugin"
        }
        register("androidLibraryConvention") {
            id = "com.mygomii.android.library"
            implementationClass = "AndroidLibraryConventionPlugin"
        }
        register("kotlinLibraryConvention") {
            id = "com.mygomii.kotlin.library"
            implementationClass = "KotlinLibraryConventionPlugin"
        }
    }
}

dependencies {
    implementation("com.android.tools.build:gradle:${libs.versions.agp.get()}")
    implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:${libs.versions.kotlin.get()}")
}

