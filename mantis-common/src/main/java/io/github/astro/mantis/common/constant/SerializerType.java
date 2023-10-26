package io.github.astro.mantis.common.constant;

import static io.github.astro.mantis.common.constant.ServiceType.Serializer;

public enum SerializerType {

    JDK(Serializer.JDK, (byte) 1),
    JSON(Serializer.JSON, (byte) 2),
    KRYO(Serializer.KRYO, (byte) 3),
    FURY(Serializer.FURY, (byte) 4);

    private final String name;

    private final byte type;

    SerializerType(String name, byte type) {
        this.name = name;
        this.type = type;
    }

    public static SerializerType get(String name) {
        for (SerializerType value : SerializerType.values()) {
            if (value.name.equals(name)) {
                return value;
            }
        }
        return null;
    }

    public static SerializerType get(byte type) {
        for (SerializerType value : SerializerType.values()) {
            if (value.type == type) {
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
}
