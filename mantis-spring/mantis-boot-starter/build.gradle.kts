dependencies {
    api("org.springframework.boot:spring-boot-starter:${version(Common.SB)}")
    api(project(":mantis-rpc"))
    //api(project(":mantis-monitor"))
    // spi
    implementation(project(":mantis-proxy:mantis-proxy-jdk"))
    implementation(project(":mantis-transport:mantis-transport-netty"))
    implementation(project(":mantis-protocol:mantis-protocol-mantis"))
    implementation(project(":mantis-eventhub:mantis-eventhub-disruptor"))
    implementation(project(":mantis-serialization:mantis-serialization-kryo"))
    implementation(project(":mantis-registry:mantis-registry-consul"))
}