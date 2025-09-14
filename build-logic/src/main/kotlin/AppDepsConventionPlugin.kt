import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType

class AppDepsConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) = with(target) {
        val libs = extensions.getByType<VersionCatalogsExtension>().named("libs")
        dependencies {
            add("implementation", libs.findLibrary("androidx.core.ktx").get())
            add("implementation", libs.findLibrary("androidx.lifecycle.runtime.ktx").get())
            add("implementation", libs.findLibrary("androidx.activity.compose").get())

            add("implementation", platform(libs.findLibrary("androidx.compose.bom").get()))
            add("implementation", libs.findLibrary("androidx.compose.ui").get())
            add("implementation", libs.findLibrary("androidx.compose.ui.graphics").get())
            add("implementation", libs.findLibrary("androidx.compose.ui.tooling.preview").get())
            add("implementation", libs.findLibrary("androidx.compose.material3").get())

            add("implementation", libs.findLibrary("androidx.room.runtime").get())
            add("implementation", libs.findLibrary("androidx.room.ktx").get())
            add("ksp", libs.findLibrary("androidx.room.compiler").get())

            add("testImplementation", libs.findLibrary("junit").get())
            add("androidTestImplementation", libs.findLibrary("androidx.junit").get())
            add("androidTestImplementation", libs.findLibrary("androidx.espresso.core").get())
            add("androidTestImplementation", platform(libs.findLibrary("androidx.compose.bom").get()))
            add("androidTestImplementation", libs.findLibrary("androidx.compose.ui.test.junit4").get())
            add("debugImplementation", libs.findLibrary("androidx.compose.ui.tooling").get())
            add("debugImplementation", libs.findLibrary("androidx.compose.ui.test.manifest").get())
        }
    }
}

