plugins {
    id("java")
    id("java-library")
}
dependencies {
    api(dependency("org.slf4j:slf4j-api"))
    api(dependency("ch.qos.logback:logback-classic"))
}
