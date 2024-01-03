package io.github.astro.mantis.common.constant;

import static io.github.astro.mantis.common.constant.KeyValues.*;

public interface Constant {

    int DEFAULT_IO_THREADS = Math.min(Runtime.getRuntime().availableProcessors() + 1, 32);

    int DEFAULT_CPU_THREADS = Runtime.getRuntime().availableProcessors();

    int DEFAULT_IO_MAX_THREADS = Runtime.getRuntime().availableProcessors() * 5;

    int DEFAULT_CPU_MAX_THREADS = Runtime.getRuntime().availableProcessors() * 2;

    int DEFAULT_CONNECT_TIMEOUT = 3 * 1000;

    int DEFAULT_MAX_CONNECT_TIMEOUT = 10 * 1000;

    int DEFAULT_TIMEOUT = 30000;

    int DEFAULT_HEARTBEAT_INTERVAL = 6000;

    int DEFAULT_SO_BACKLOG = 1024;

    int DEFAULT_SESSION_TIMEOUT = 60 * 1000;

    int DEFAULT_INTERVAL = 1000;

    int DEFAULT_RETIRES = 3;

    int DEFAULT_CAPACITY = Runtime.getRuntime().availableProcessors() * 100;

    int DEFAULT_KEEPALIVE = 60;

    int DEFAULT_PROTOCOL_PORT = 2333;

    int DEFAULT_MAX_MESSAGE_SIZE = 1024 * 32;

    String DEFAULT_SERIALIZE = Serialize.KRYO;

    int DEFAULT_MAX_HEADER_SIZE = 10000;

    int DEFAULT_BUFFER_SIZE = 1024 * 1024;

    int DEFAULT_SPARE_CLOSE_TIMES = 10;

    String DEFAULT_VERSION = "1.0.0";

    String DEFAULT_GROUP = "default:group";

    int DEFAULT_WEIGHT = 0;

    String DEFAULT_PROXY = ProxyFactory.JDK;

    String DEFAULT_REGISTRY = RegistryFactory.ZOOKEEPER;

    String DEFAULT_FAULT_TOLERANCE = FaultTolerance.FAIL_RETRY;

    String DEFAULT_LOAD_BALANCE = LoadBalance.RANDOM;

    String DEFAULT_EVENT_DISPATCHER = EventDispatcher.DISRUPTOR;

    String LOCAL_ZK_ADDRESS = "127.0.0.1:2181";

    String SERVICES_DIR = "/mantis/services";

    String SPI_FIX_PATH = "META-INF/services/";

    String MONITOR_APPLICATION_NAME = "inner-monitor";

    String MONITOR_remoteService_NAME = "mantisMonitor";

    String MONITOR_remoteService = "monitorRemoteService";

}
