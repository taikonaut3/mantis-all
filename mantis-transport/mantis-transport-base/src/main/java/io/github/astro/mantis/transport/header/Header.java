package io.github.astro.mantis.transport.header;

import io.github.astro.mantis.common.constant.Key;
import io.github.astro.mantis.common.constant.Mode;
import io.github.astro.mantis.common.constant.ModeContainer;
import io.github.astro.mantis.configuration.spi.ExtensionLoader;
import io.github.astro.mantis.serialization.Serializer;

import java.util.Map;

public interface Header {

    int getLength();

    int getAllowMaxSize();

    Mode getEnvelopeMode();

    Mode getProtocolMode();

    Map<String, String> getExtendsData();

    void addExtendData(String key, String value);

    void addExtendData(Map<String, String> extendData);

    String getExtendData(String key);

    byte[] fixDataToBytes();

    byte[] extendDataToBytes();

    default Mode getSerializerMode() {
        String serial = getExtendData(Key.SERIALIZE);
        return ModeContainer.getMode(Key.SERIALIZE, serial);
    }

    default Serializer getSerializer() {
        String serial = getExtendData(Key.SERIALIZE);
        return ExtensionLoader.loadService(Serializer.class, serial);
    }

}
