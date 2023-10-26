plugins {
    id("java")
    id("java-library")
}
dependencies {
    api(project(":mantis-protocol:mantis-protocol-base"))
    api(project(":mantis-registry:mantis-registry-base"))
    api(project(":mantis-proxy:mantis-proxy-base"))
}