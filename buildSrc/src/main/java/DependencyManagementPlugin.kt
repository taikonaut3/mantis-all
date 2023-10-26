import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.extra
import kotlin.collections.set


class DependencyManagementPlugin : Plugin<Project> {
    override fun apply(project: Project) {
        if (project == project.rootProject) {
            project.extensions.create(Common.DM, DependencyManagementExtension::class.java)
            val dependencyManagement =
                project.rootProject.extensions.findByType(DependencyManagementExtension::class.java)
            project.afterEvaluate {
                project.extra.set(Common.DM, dependencyManagement)
            }
        }
    }
}

open class DependencyManagementExtension {
    private val dependencies = mutableMapOf<String, String>()
    private val versions = mutableMapOf<String, String>()

    fun getDependency(dependency: String): String {
        return dependency + ":" + (dependencies[dependency] ?: "")
    }

    fun getDependency(group: String, artifact: String): String {
        val dependency = "$group:$artifact"
        return getDependency(dependency)
    }

    fun getVersion(key: String): String {
        return versions[key] ?: ""
    }

    fun version(key: String, value: String) {
        versions[key] = value
    }

    fun dependency(dependency: String, version: String) {
        dependencies[dependency] = version
    }


}
