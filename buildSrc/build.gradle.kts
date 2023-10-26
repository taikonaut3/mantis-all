plugins {
    `kotlin-dsl`
}
repositories {
    mavenCentral()
}

gradlePlugin {
    plugins {
        create("dependencyManagement") {
            id = "mantis-dependencyManagement"
            implementationClass = "DependencyManagementPlugin"
        }
    }
}