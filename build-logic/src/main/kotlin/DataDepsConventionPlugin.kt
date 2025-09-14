import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType

class DataDepsConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) = with(target) {
        val libs = extensions.getByType<VersionCatalogsExtension>().named("libs")
        dependencies {
            add("implementation", libs.findLibrary("kotlinx.coroutines.core").get())

            add("api", libs.findLibrary("ktor.client.android").get())
            add("api", libs.findLibrary("ktor.client.content.negotiation").get())
            add("api", libs.findLibrary("ktor.serialization.kotlinx.json").get())
            add("api", libs.findLibrary("ktor.client.logging").get())

            add("implementation", libs.findLibrary("androidx.room.runtime").get())
            add("implementation", libs.findLibrary("androidx.room.ktx").get())
            add("ksp", libs.findLibrary("androidx.room.compiler").get())
        }
    }
}

