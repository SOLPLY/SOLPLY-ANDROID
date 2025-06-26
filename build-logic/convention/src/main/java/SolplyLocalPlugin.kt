import org.gradle.api.Plugin
import org.gradle.api.Project

class SolplyLocalPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("solply.android.library")
                apply("solply.android.hilt")
            }
        }
    }
}