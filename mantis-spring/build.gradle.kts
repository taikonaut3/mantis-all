import groovy.util.Node
import groovy.util.NodeList
import org.gradle.api.internal.artifacts.dependencies.DefaultProjectDependency

subprojects {
    apply(plugin = "java")
    apply(plugin = "java-library")
    dependencies {
        add("compileOnly", "org.springframework.boot:spring-boot-configuration-processor:${version(Common.SB)}")
        add("annotationProcessor", "org.springframework.boot:spring-boot-configuration-processor:${version(Common.SB)}")
    }
    configure<PublishingExtension> {
        publications {
            create<MavenPublication>(name) {
                groupId = rootProject.group.toString()
                artifactId = project.name
                version = rootProject.version.toString()
                from(components["java"])
                pom.withXml {
                    val xmlNode = asNode()
                    val depNodes = xmlNode["dependencies"] as NodeList
                    val dependencies = depNodes[0] as Node
                    dependencies.children().clear()
                    val configuration = configurations["runtimeClasspath"]
                    configuration.allDependencies.forEach{ dep ->
                        if (dep is DefaultProjectDependency) {
                            if (configuration.excludeRules.isEmpty()) {
                                val dependencyNode = dependencies.appendNode("dependency")
                                dependencyNode.appendNode("groupId", groupId)
                                dependencyNode.appendNode("artifactId", dep.name)
                                dependencyNode.appendNode("version", version)
                                dependencyNode.appendNode("scope", "compile")
                            }else{
                                configuration.excludeRules.forEach {
                                    if (it.group != dep.group && it.module != dep.name) {
                                        val dependencyNode = dependencies.appendNode("dependency")
                                        dependencyNode.appendNode("groupId", groupId)
                                        dependencyNode.appendNode("artifactId", dep.name)
                                        dependencyNode.appendNode("version", version)
                                        dependencyNode.appendNode("scope", "compile")
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        repositories {
            maven {
                url = uri(Common.mavenUrl)
            }
        }
    }
}