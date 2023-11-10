package io.github.astro;

import io.github.astro.mantis.common.exception.ConversionException;
import io.github.astro.mantis.common.exception.SerializationException;
import io.github.astro.mantis.configuration.spi.ServiceProvider;
import io.github.astro.mantis.serialization.Serializer;
import io.guthub.astro.mantis.proxy.InvocationHandler;
import io.guthub.astro.mantis.proxy.ProxyFactory;

import java.lang.reflect.Type;

/**
 * @Author WenBo Zhou
 * @Date 2023/9/13 9:17
 */
@ServiceProvider(value = "test", interfaces = {Serializer.class})
public class MyService implements Serializer, ProxyFactory {

    @Override
    public <T> T createProxy(Class<T> interfaceClass, InvocationHandler handler) {
        return null;
    }

    @Override
    public <T> T createProxy(T target, InvocationHandler handler) {
        return null;
    }

    @Override
    public Object[] convert(Object[] args, Type[] type) throws ConversionException {
        return new Object[0];
    }

    @Override
    public Object convert(Object arg, Type type) throws ConversionException {
        return null;
    }

    @Override
    public byte[] serialize(Object input) throws SerializationException {
        return new byte[0];
    }

    @Override
    public <T> T deserialize(byte[] bytes, Class<T> clazz) throws SerializationException {
        return null;
    }

}
