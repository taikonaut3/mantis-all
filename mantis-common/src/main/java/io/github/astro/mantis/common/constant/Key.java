package io.github.astro.mantis.common.constant;

public interface Key {

    String UNIQUE_ID = "uniqueId";

    String APPLICATION_NAME = "applicationName";

    String SERVICE_NAME = "serviceName";

    String PROTOCOL = "protocol";

    String EVENT_DISPATCHER = "eventDispatcher";

    String SERVER_EVENT_DISPATCHER = "serverEventDispatcher";

    String PROTOCOL_VERSION = "protocolVersion";

    String CLASS = "class";

    String BODY_TYPE = "bodyType";

    String REGISTERS = "registers";

    String IS_ONEWAY = "isOneWay";

    String RETRY_INTERVAL = "retryInterval";

    String ENABLED = "enabled";

    String VERSION = "version";

    String GROUP = "group";

    String WEIGHT = "weight";

    String MSG_TYPE = "msgType";

    String TIMEOUT = "timeout";

    String HEARTBEAT_INTERVAL = "heartbeatInterval";

    String SPARE_CLOSE_TIMES = "spareCloseTimes";

    String HEARTBEAT_LOG_ENABLE = "heartbeatLogEnable";

    String READER_IDLE_TIMES = "readeIdleTimes";

    String WRITE_IDLE_TIMES = "writeIdleTimes";

    String ALL_IDLE_TIMES = "allIdleTimes";

    String SO_BACKLOG = "soBacklog";

    String IS_ASYNC = "isAsync";

    String PROXY = "proxy";

    String TRANSPORT = "transport";

    String SERIALIZE = "serialize";

    String LOAD_BALANCE = "loadBalance";

    String DIRECTORY = "directory";

    String ROUTER = "router";

    String RETRIES = "retries";

    String FAULT_TOLERANCE = "faultTolerance";

    String CHECK = "check";

    String TIMESTAMP = "timestamp";

    String DYNAMIC = "dynamic";

    String SUBSCRIBE = "subscribe";

    String SERVER_MAX_RECEIVE_SIZE = "serverMaxReceiveSize";

    String CLIENT_MAX_RECEIVE_SIZE = "clientMaxReceiveSize";

    String MAX_HEADER_SIZE = "maxHeaderSize";

    String SSL = "ssl";

    String CONNECT_TIMEOUT = "connectTimeout";

    String COMPRESSION = "compression";

    String KEEPALIVE = "keepAlive";

    String GLOBAL = "global";

    String URL = "url";

    String ENVELOPE = "envelope";

    String RESPONSE_CODE = "responseCode";

    String INVOKER = "invoker";

    String RETURN_TYPE = "returnType";

    String PASSWORD = "password";

    String USERNAME = "username";

    String SESSION_TIMEOUT = "sessionTimeout";

    String INTERVAL = "interval";

    String DESCRIPTION = "description";

    String MANTIS_BOOTSTRAP = "mantisBootstrap";

    String MANTIS = "mantis_";

    String PROTOCOL_PREFIX = MANTIS + "protocols_";

    String REGISTRY_META_PROTOCOL = MANTIS + "protocols_";

    String REGISTRY_META_WEIGHT = MANTIS + WEIGHT;

    String REGISTRY_META_MONITOR_ENABLED = MANTIS + "monitor_enabled";

}
