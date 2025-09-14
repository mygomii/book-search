import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType

class PresentationDepsConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) = with(target) {
        val libs = extensions.getByType<VersionCatalogsExtension>().named("libs")
        dependencies {
            add("implementation", libs.findLibrary("androidx.activity.compose").get())
            add("implementation", platform(libs.findLibrary("androidx.compose.bom").get()))
            add("implementation", libs.findLibrary("androidx.compose.ui").get())
            add("implementation", libs.findLibrary("androidx.compose.ui.graphics").get())
            add("implementation", libs.findLibrary("androidx.compose.ui.tooling.preview").get())
            add("implementation", libs.findLibrary("androidx.compose.material3").get())
            add("implementation", libs.findLibrary("androidx.compose.material.icons.extended").get())
            add("implementation", libs.findLibrary("androidx.navigation.compose").get())
            add("implementation", libs.findLibrary("coil.compose").get())
            add("implementation", libs.findLibrary("kotlinx.coroutines.android").get())
            add("debugImplementation", libs.findLibrary("androidx.compose.ui.tooling").get())
        }
    }
}

