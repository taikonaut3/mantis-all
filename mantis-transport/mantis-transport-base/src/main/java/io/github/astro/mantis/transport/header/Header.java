package io.github.astro.mantis.transport.header;

import io.github.astro.mantis.common.constant.Key;
import io.github.astro.mantis.common.constant.MessageType;
import io.github.astro.mantis.common.constant.SerializerType;
import io.github.astro.mantis.configuration.extension.spi.ServiceProviderLoader;
import io.github.astro.mantis.serialization.Serializer;

import java.util.Map;

public interface Header {

    int getLength();

    MessageType getMessageType();

    String getProtocolVersion();

    Map<String, String> getExtendsData();

    void addExtendData(String key, String value);

    void addExtendData(Map<String, String> extendData);

    String getExtendData(String key);

    byte[] toBytes();

    default SerializerType getSerializerType() {
        String serial = getExtendData(Key.SERIALIZE);
        return SerializerType.get(serial);
    }

    default Serializer getSerializer() {
        String serial = getExtendData(Key.SERIALIZE);
        return ServiceProviderLoader.loadService(Serializer.class, serial);
    }

}
