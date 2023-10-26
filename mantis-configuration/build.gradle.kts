plugins {
    id("java")
    id("java-library")
}
dependencies {
    api(project(":mantis-common"))
    compileOnly(dependency("org.projectlombok:lombok"))
    annotationProcessor(dependency("org.projectlombok:lombok"))
}