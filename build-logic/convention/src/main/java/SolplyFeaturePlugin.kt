import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies
import com.teamsolply.solply.convention.extension.getBundle
import com.teamsolply.solply.convention.extension.getLibrary
import com.teamsolply.solply.convention.extension.implementation
import com.teamsolply.solply.convention.extension.libs

class SolplyFeaturePlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("solply.android.compose.library")
                apply("solply.android.hilt")
                apply("org.jetbrains.kotlin.plugin.serialization")
            }

            dependencies {
                implementation(libs.getLibrary("kotlinx.serialization.json"))
                implementation(project(":core:ui"))
                implementation(project(":core:designsystem"))
                implementation(project(":core:model"))
                implementation(project(":core:navigation"))
                implementation(libs.getBundle("compose"))
            }
        }
    }
}