package io.github.astro.mantis.common.constant;

import static io.github.astro.mantis.common.constant.ServiceType.RegistryFactory;

public enum RegistryType {

    ZOOKEEPER(RegistryFactory.ZOOKEEPER),
    CONSUL(RegistryFactory.CONSUL);

    private final String name;

    RegistryType(String name) {
        this.name = name;
    }

    public static RegistryType get(String name) {
        for (RegistryType value : values()) {
            if (value.name.equals(name)) {
                return value;
            }
        }
        return null;
    }

    public String getName() {
        return name;
    }
}
