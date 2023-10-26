dependencies {
    dependencies {
        implementation(project(":mantis-registry:mantis-registry-base"))
        implementation(dependency("org.apache.curator:curator-framework"))
        implementation(dependency("org.apache.curator:curator-recipes"))
    }
}