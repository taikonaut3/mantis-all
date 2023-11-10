dependencies {
    api("org.springframework.boot:spring-boot-starter:${version(Common.SB)}")
    api(project(":mantis-rpc"))
    //api(project(":mantis-monitor"))
    // spi
    api(project(":mantis-proxy:mantis-proxy-jdk"))
    api(project(":mantis-transport:mantis-transport-netty"))
    api(project(":mantis-protocol:mantis-protocol-mantis"))
    api(project(":mantis-eventhub:mantis-eventhub-disruptor"))
    api(project(":mantis-serialization:mantis-serialization-kryo"))
    api(project(":mantis-registry:mantis-registry-consul"))
}