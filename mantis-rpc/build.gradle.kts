plugins {
    id("java")
    id("java-library")
}
dependencies {
    api(project(":mantis-governance"))
    api(project(":mantis-protocol:mantis-protocol-mantis"))
}