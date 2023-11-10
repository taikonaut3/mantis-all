plugins {
    id("io.spring.dependency-management") version ("1.1.0")
}
dependencyManagement {
    imports {
        mavenBom("org.springframework.cloud:spring-cloud-dependencies:${version(Common.SC)}")
    }
}

dependencies {
    api(project(":mantis-demo:model"))
    api(project(":mantis-spring:spring-cloud-rpc-mantis"))
    api("org.springframework.boot:spring-boot-starter-web:${version(Common.SB)}")
    // https://mvnrepository.com/artifact/org.springframework.boot/spring-boot-starter-actuator
    api("org.springframework.boot:spring-boot-starter-actuator:${version(Common.SB)}")
    implementation("org.springframework.cloud:spring-cloud-starter-consul-discovery")
    api(project(":mantis-serialization:mantis-serialization-fury"))
    api(project(":mantis-serialization:mantis-serialization-json"))
}