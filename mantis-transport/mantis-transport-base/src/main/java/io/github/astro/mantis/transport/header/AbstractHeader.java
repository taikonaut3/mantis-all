package io.github.astro.mantis.transport.header;

import io.github.astro.mantis.common.constant.MessageType;

import java.util.HashMap;
import java.util.Map;

public abstract class AbstractHeader implements Header {

    private MessageType messageType;
    private String protocolVersion;
    private volatile Map<String, String> extendData;

    protected AbstractHeader(MessageType messageType, String protocolVersion) {
        this.messageType = messageType;
        this.protocolVersion = protocolVersion;
    }

    @Override
    public int getLength() {
        byte[] bytes = toBytes();
        // 减去两个长度int 4 字节
        return bytes.length - 8;
    }

    @Override
    public MessageType getMessageType() {
        return messageType;
    }

    @Override
    public String getProtocolVersion() {
        return protocolVersion;
    }

    @Override
    public Map<String, String> getExtendsData() {
        return extendData;
    }

    @Override
    public void addExtendData(Map<String, String> extendData) {
        this.extendData = extendData;
    }

    @Override
    public void addExtendData(String key, String value) {
        if (extendData == null) {
            synchronized (this) {
                if (extendData == null) {
                    extendData = new HashMap<>();
                }
            }
        }
        extendData.put(key, value);
    }

    @Override
    public String getExtendData(String key) {
        return extendData.get(key);
    }
}
