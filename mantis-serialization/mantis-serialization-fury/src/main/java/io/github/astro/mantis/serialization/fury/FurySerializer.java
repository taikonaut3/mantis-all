package io.github.astro.mantis.serialization.fury;

import io.fury.Fury;
import io.fury.Language;
import io.fury.ThreadSafeFury;
import io.github.astro.mantis.common.exception.SerializationException;
import io.github.astro.mantis.configuration.extension.spi.ServiceProvider;
import io.github.astro.mantis.serialization.Serializer;

import static io.github.astro.mantis.common.constant.ServiceType.Serializer.FURY;

/**
 * 测试出 fury的序列化并不理想
 */
@ServiceProvider(FURY)
public class FurySerializer implements Serializer {

    private ThreadSafeFury fury;

    public FurySerializer() {
        fury = Fury.builder().withLanguage(Language.JAVA)
                .withSecureMode(false)
                .buildThreadSafeFury();
    }

    @Override
    public byte[] serialize(Object input) throws SerializationException {
        return fury.serialize(input);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> T deserialize(byte[] bytes, Class<T> clazz) throws SerializationException {
        return (T) fury.deserialize(bytes);
    }
}
