plugins {
    id("io.spring.dependency-management") version ("1.1.0")
}
dependencyManagement {
    imports {
        mavenBom("org.springframework.cloud:spring-cloud-dependencies:${version(Common.SC)}")
    }
}

dependencies {
    api("org.springframework.cloud:spring-cloud-commons")
    api("org.springframework.boot:spring-boot-starter-web:${version(Common.SB)}")
    // https://mvnrepository.com/artifact/org.springframework.boot/spring-boot-starter-actuator
    api("org.springframework.boot:spring-boot-starter-actuator:${version(Common.SB)}")
    api(project(":mantis-spring:mantis-boot-starter")) {
        configurations.configureEach {
            exclude(module = "mantis-registry-consul")
        }
    }
}