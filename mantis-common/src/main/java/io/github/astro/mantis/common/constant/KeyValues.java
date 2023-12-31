package io.github.astro.mantis.common.constant;

public interface KeyValues {

    String SPRING = "spring";

    interface EventDispatcher {

        String FLOW = "flow";

        String DISRUPTOR = "disruptor";

    }

    interface ProxyFactory {

        String JDK = "jdk";

        String CGLIB = "cglib";

        String BYTEBUDDY="byteBuddy";

    }

    interface Protocol {

        String MANTIS = "mantis";

        String HTTP1_1 = "http1.1";

    }

    interface RegistryFactory {

        String ZOOKEEPER = "zookeeper";

        String CONSUL = "consul";

        String REDIS = "redis";

    }

    interface Serialize {

        String JDK = "jdk";

        String JSON = "json";

        String FURY = "fury";

        String KRYO = "kryo";

    }

    interface Transport {

        String NETTY = "netty";

    }

    interface FaultTolerance {

        String FAIL_RETRY = "failRetry";

    }

    interface LoadBalance {

        String RANDOM = "random";

        String ROUND_ROBIN = "roundRobin";

        String WEIGHTED_ROUND_ROBIN = "WeightedRoundRobin";

    }

    interface Router {

        String WEIGHT = "weight";

    }

    interface Directory {

        String DEFAULT = "default";

    }

    interface Envelope {

        String REQUEST = "request";

        String RESPONSE = "response";

        String HEARTBEAT = "heartbeat";

        String ERROR = "error";

    }

}
