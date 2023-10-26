dependencies {
    implementation(project(":mantis-registry:mantis-registry-base"))
    implementation(dependency("com.ecwid.consul:consul-api"))
    compileOnly("org.apache.httpcomponents:httpclient:4.5.5")
}