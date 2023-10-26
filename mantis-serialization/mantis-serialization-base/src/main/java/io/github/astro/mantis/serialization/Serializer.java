package io.github.astro.mantis.serialization;

import io.github.astro.mantis.common.exception.SerializationException;
import io.github.astro.mantis.configuration.extension.spi.ServiceInterface;

import static io.github.astro.mantis.common.constant.ServiceType.Serializer.KRYO;

@ServiceInterface(KRYO)
public interface Serializer extends Converter {
    byte[] serialize(Object input) throws SerializationException;

    <T> T deserialize(byte[] bytes, Class<T> clazz) throws SerializationException;

}
