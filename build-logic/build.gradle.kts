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
        register("appDepsConvention") {
            id = "com.mygomii.app.deps"
            implementationClass = "AppDepsConventionPlugin"
        }
        register("presentationDepsConvention") {
            id = "com.mygomii.presentation.deps"
            implementationClass = "PresentationDepsConventionPlugin"
        }
        register("designSystemDepsConvention") {
            id = "com.mygomii.designsystem.deps"
            implementationClass = "DesignSystemDepsConventionPlugin"
        }
        register("dataDepsConvention") {
            id = "com.mygomii.data.deps"
            implementationClass = "DataDepsConventionPlugin"
        }
        register("domainDepsConvention") {
            id = "com.mygomii.domain.deps"
            implementationClass = "DomainDepsConventionPlugin"
        }
    }
}

dependencies {
    implementation("com.android.tools.build:gradle:${libs.versions.agp.get()}")
    implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:${libs.versions.kotlin.get()}")
}
