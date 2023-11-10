import org.gradle.api.Project

object Common {

    const val DM = "dependencyManagement"

    const val S = "spring"

    const val SB = "springBoot"

    const val SC = "springCloud"

    const val MV = "mantis.version"

    // 所有子项目通用的模块
    val commonModules = listOf("mantis-common", "mantis-configuration")

    // 打包发布排除的模块
    val excludedPublishModules = listOf("mantis-demo", "mantis-test", "mantis-spring")

    //  maven 仓库地址
    const val mavenUrl = "C:/dev/mvn_repository"

}

fun Project.dependency(dependency: String): String {
    return rootProject.extensions.getByType(DependencyManagementExtension::class.java).getDependency(dependency)
}

fun Project.version(key: String): String {
    return rootProject.extensions.getByType(DependencyManagementExtension::class.java).getVersion(key)
}

fun Project.getAllProjects(modules: List<String>): List<Project> {
    val projects = mutableListOf<Project>()
    for (excludedProjectName in modules) {
        val project = rootProject.findProject(excludedProjectName)
        if (project != null) {
            projects.addAll(project.allprojects)
        }
    }
    return projects
}