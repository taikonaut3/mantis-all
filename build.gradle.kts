apply(plugin = "mantis-dependencyManagement")

group = "io.github.astro"
version = "1.0.0"

// 依赖管理
configure<DependencyManagementExtension> {
    dependency("io.netty:netty-all", "4.1.86.Final")
    dependency("org.apache.curator:curator-framework", "5.5.0")
    dependency("org.apache.curator:curator-recipes", "5.5.0")
    dependency("com.fasterxml.jackson.core:jackson-databind", "2.15.0")
    dependency("com.lmax:disruptor", "3.4.4")
    dependency("net.bytebuddy:byte-buddy", "1.14.4")
    dependency("com.esotericsoftware:kryo", "5.5.0")
    dependency("org.furyio:fury-core", "0.1.0-alpha.2")
    dependency("org.slf4j:slf4j-api", "2.0.7")
    dependency("ch.qos.logback:logback-classic", "1.4.6")
    dependency("org.projectlombok:lombok", "1.18.28")
    dependency("com.ecwid.consul:consul-api", "1.4.5")
    version(Common.SB, "3.0.9")
    version(Common.SC, "2022.0.4")
}
subprojects {
    apply(plugin = "maven-publish")
    group = rootProject.group
    version = rootProject.version

    afterEvaluate {
        if (plugins.hasPlugin(JavaPlugin::class)) {
            if (!Common.commonModules.contains(name)) {
                dependencies {
                    add("api", project(":mantis-configuration"))
                    add("annotationProcessor", project(":mantis-configuration"))
                }
            }
            if (!getAllProjects(Common.excludedPublishModules).contains(project)) {
                configure<PublishingExtension> {
                    publications {
                        create<MavenPublication>(name) {
                            from(components["java"])
                        }
                    }
                    repositories {
                        maven {
                            url = uri(Common.mavenUrl)
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
        mavenLocal()
        mavenCentral()
    }
}


