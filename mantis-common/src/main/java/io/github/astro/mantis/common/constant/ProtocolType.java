package io.github.astro.mantis.common.constant;

import static io.github.astro.mantis.common.constant.ServiceType.Protocol;
import static io.github.astro.mantis.common.constant.ServiceType.RegistryFactory;

public enum ProtocolType {

    MANTIS(Protocol.MANTIS, (byte) 0, "1.0"),
    HTTP11(Protocol.HTTP1_1, (byte) 1, "1.1"),

    //------------------Registry-----------------------
    ZOOKEEPER(RegistryFactory.ZOOKEEPER),
    CONSUL(RegistryFactory.CONSUL),
    REDIS(RegistryFactory.REDIS);

    private final String name;
    private byte type;
    private String version;

    ProtocolType(String name) {
        this.name = name;
    }

    ProtocolType(String name, Byte type, String version) {
        this.name = name;
        this.type = type;
        this.version = version;
    }

    public static String get(byte type) {
        for (ProtocolType value : ProtocolType.values()) {
            if (value.type == type) {
                return value.name;
            }
        }
        return null;
    }

    public static ProtocolType get(String name) {
        for (ProtocolType value : ProtocolType.values()) {
            if (value.name.equals(name)) {
                return value;
            }
        }
        return null;
    }

    public String getName() {
        return name;
    }

    public byte getType() {
        return type;
    }

    public String getVersion() {
        return version;
    }
}
