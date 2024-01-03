plugins {
    id("java")
    id("java-library")
}
dependencies {
    api(project(":mantis-transport:mantis-transport-base"))
    api(project(":mantis-registry:mantis-registry-base"))
    api(project(":mantis-proxy:mantis-proxy-base"))
}