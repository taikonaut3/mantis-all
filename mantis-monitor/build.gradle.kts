plugins {
    id("java")
    id("java-library")
}
dependencies {
    compileOnly(dependency("org.projectlombok:lombok"))
    annotationProcessor(dependency("org.projectlombok:lombok"))
}