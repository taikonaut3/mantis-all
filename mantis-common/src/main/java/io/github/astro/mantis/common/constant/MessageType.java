package io.github.astro.mantis.common.constant;

public enum MessageType {

    REQUEST((byte) 1),
    RESPONSE((byte) 2),
    HEARTBEAT((byte) 3),
    EXCEPTION((byte) -1);

    private final byte type;

    MessageType(byte type) {

        this.type = type;
    }

    public static MessageType get(byte type) {
        for (MessageType value : MessageType.values()) {
            if (value.type == type) {
                return value;
            }
        }
        return null;
    }

    public byte getType() {
        return type;
    }
}
