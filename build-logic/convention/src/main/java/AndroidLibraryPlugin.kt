import com.android.build.api.dsl.LibraryExtension
import com.teamsolply.solply.convention.configureKotlinAndroid
import com.teamsolply.solply.convention.configureKotlinCoroutine
import com.teamsolply.solply.convention.extension.getLibrary
import com.teamsolply.solply.convention.extension.implementation
import com.teamsolply.solply.convention.extension.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies

class AndroidLibraryPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("com.android.library")
            }

            extensions.configure<LibraryExtension> {
                configureKotlinAndroid(this)
                configureKotlinCoroutine(this)
            }

            dependencies {
                implementation(libs.getLibrary("timber"))
            }
        }
    }
}