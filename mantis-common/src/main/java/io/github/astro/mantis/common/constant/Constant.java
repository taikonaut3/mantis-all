package io.github.astro.mantis.common.constant;

import static io.github.astro.mantis.common.constant.ServiceType.*;

public interface Constant {

    int DEFAULT_IO_THREADS = Math.min(Runtime.getRuntime().availableProcessors() + 1, 32);
    int DEFAULT_CPU_THREADS = Runtime.getRuntime().availableProcessors();
    int DEFAULT_IO_MAX_THREADS = Runtime.getRuntime().availableProcessors() * 5;
    int DEFAULT_CPU_MAX_THREADS = Runtime.getRuntime().availableProcessors() * 2;
    int DEFAULT_CONNECT_TIMEOUT = 30 * 1000;
    int DEFAULT_TIMEOUT = 3000;
    int DEFAULT_SESSION_TIMEOUT = 60 * 1000;
    int DEFAULT_INTERVAL = 1000;
    int DEFAULT_RETIRES = 3;
    int DEFAULT_CAPACITY = Runtime.getRuntime().availableProcessors() * 100;
    int DEFAULT_KEEPALIVE = 60;
    int DEFAULT_PROTOCOL_PORT = 2333;
    int DEFAULT_MAX_MESSAGE_SIZE = 90000;

    int DEFAULT_BUFFER_SIZE = 1024 * 1024;
    String DEFAULT_VERSION = "1.0.0";
    String DEFAULT_GROUP = "default:group";
    int DEFAULT_WEIGHT = 0;
    String DEFAULT_SERIALIZE = Serializer.KRYO;
    String DEFAULT_PROTOCOL = Protocol.MANTIS;
    String DEFAULT_PROXY = ProxyFactory.JDK;
    String DEFAULT_REGISTRY = RegistryFactory.ZOOKEEPER;
    String DEFAULT_FAULT_TOLERANCE = FaultTolerance.FAIL_RETRY;
    String DEFAULT_LOAD_BALANCE = LoadBalance.RANDOM;
    String LOCAL_ZK_ADDRESS = "127.0.0.1:2181";
    String SERVICES_DIR = "/mantis/services";
    String FIX_PATH = "META-INF/services/";

    String MONITOR_APPLICATION_NAME = "inner-monitor";

    String MONITOR_EXPORTER_NAME = "mantisMonitor";

    String MONITOR_EXPORTER = "monitorExporter";

}
