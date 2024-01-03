rootProject.name = "mantis-all"
include("mantis-common")
include("mantis-configuration")
include("mantis-eventhub")
include("mantis-governance")
include("mantis-eventhub:mantis-eventhub-base")
findProject(":mantis-eventhub:mantis-eventhub-base")?.name = "mantis-eventhub-base"
include("mantis-eventhub:mantis-eventhub-disruptor")
findProject(":mantis-eventhub:mantis-eventhub-disruptor")?.name = "mantis-eventhub-disruptor"
include("mantis-eventhub:mantis-eventhub-flow")
findProject(":mantis-eventhub:mantis-eventhub-flow")?.name = "mantis-eventhub-flow"
include("mantis-protocol")
include("mantis-protocol:mantis-protocol-base")
findProject(":mantis-protocol:mantis-protocol-base")?.name = "mantis-protocol-base"
include("mantis-protocol:mantis-protocol-mantis")
findProject(":mantis-protocol:mantis-protocol-mantis")?.name = "mantis-protocol-mantis"
include("mantis-proxy")
include("mantis-proxy:mantis-proxy-base")
findProject(":mantis-proxy:mantis-proxy-base")?.name = "mantis-proxy-base"
include("mantis-proxy:mantis-proxy-cglib")
findProject(":mantis-proxy:mantis-proxy-cglib")?.name = "mantis-proxy-cglib"
include("mantis-proxy:mantis-proxy-jdk")
findProject(":mantis-proxy:mantis-proxy-jdk")?.name = "mantis-proxy-jdk"
include("mantis-registry")
include("mantis-registry:mantis-registry-base")
findProject(":mantis-registry:mantis-registry-base")?.name = "mantis-registry-base"
include("mantis-registry:mantis-registry-zookeeper")
findProject(":mantis-registry:mantis-registry-zookeeper")?.name = "mantis-registry-zookeeper"
include("mantis-registry:mantis-registry-consul")
findProject(":mantis-registry:mantis-registry-consul")?.name = "mantis-registry-consul"
include("mantis-rpc")
include("mantis-serialization")
include("mantis-serialization:mantis-serialization-base")
findProject(":mantis-serialization:mantis-serialization-base")?.name = "mantis-serialization-base"
include("mantis-serialization:mantis-serialization-jdk")
findProject(":mantis-serialization:mantis-serialization-jdk")?.name = "mantis-serialization-jdk"
include("mantis-serialization:mantis-serialization-kryo")
findProject(":mantis-serialization:mantis-serialization-kryo")?.name = "mantis-serialization-kryo"
include("mantis-serialization:mantis-serialization-json")
findProject(":mantis-serialization:mantis-serialization-json")?.name = "mantis-serialization-json"
include("mantis-serialization:mantis-serialization-fury")
findProject(":mantis-serialization:mantis-serialization-fury")?.name = "mantis-serialization-fury"
include("mantis-transport")
include("mantis-transport:mantis-transport-base")
findProject(":mantis-transport:mantis-transport-base")?.name = "mantis-transport-base"
include("mantis-transport:mantis-transport-netty")
findProject(":mantis-transport:mantis-transport-netty")?.name = "mantis-transport-netty"
include("mantis-spring")
include("mantis-spring:mantis-boot-starter")
findProject(":mantis-spring:mantis-boot-starter")?.name = "mantis-boot-starter"
include("mantis-spring:spring-cloud-rpc-mantis")
findProject(":mantis-spring:spring-cloud-rpc-mantis")?.name = "spring-cloud-rpc-mantis"
include("mantis-monitor")
include("mantis-demo")
include("mantis-demo:consumer")
findProject(":mantis-demo:consumer")?.name = "consumer"
include("mantis-demo:provider")
findProject(":mantis-demo:provider")?.name = "provider"
include("mantis-demo:model")
findProject(":mantis-demo:model")?.name = "model"
include("mantis-proxy:mantis-proxy-bytebuddy")
findProject(":mantis-proxy:mantis-proxy-bytebuddy")?.name = "mantis-proxy-bytebuddy"
include("mantis-transport:mantis-transport-http")
findProject(":mantis-transport:mantis-transport-http")?.name = "mantis-transport-http"
