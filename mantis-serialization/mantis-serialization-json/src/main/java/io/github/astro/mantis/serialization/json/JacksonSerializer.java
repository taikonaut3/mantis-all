package io.github.astro.mantis.serialization.json;

import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import io.github.astro.mantis.common.exception.ConversionException;
import io.github.astro.mantis.common.exception.SerializationException;
import io.github.astro.mantis.configuration.spi.ServiceProvider;
import io.github.astro.mantis.serialization.Serializer;

import java.io.IOException;
import java.lang.reflect.Type;

import static io.github.astro.mantis.common.constant.KeyValues.Serialize.JSON;

@ServiceProvider(JSON)
public class JacksonSerializer implements Serializer {

    private final ObjectMapper objectMapper;

    public JacksonSerializer() {
        this.objectMapper = JsonMapper.builder().configure(MapperFeature.PROPAGATE_TRANSIENT_MARKER, true).build();
    }

    @Override
    public byte[] serialize(Object input) throws SerializationException {
        try {
            return objectMapper.writeValueAsBytes(input);
        } catch (Throwable e) {
            e.printStackTrace();
            throw new SerializationException(e);
        }
    }

    @Override
    public <T> T deserialize(byte[] bytes, Class<T> clazz) throws SerializationException {
        try {
            return objectMapper.readValue(bytes, clazz);
        } catch (IOException e) {
            e.printStackTrace();
            throw new SerializationException(e);
        }
    }

    @Override
    public Object[] convert(Object[] args, Type[] types) throws ConversionException {
        Object[] objects = new Object[types.length];
        try {
            // 遍历 Object[] 数组并匹配相应类型
            for (int i = 0; i < args.length; i++) {
                Object deserializedObject = args[i];
                Type objectType = types[i];
                // 将反序列化的对象转换为指定类型
                Object typedObject = objectMapper.convertValue(deserializedObject, objectMapper.constructType(objectType));
                objects[i] = typedObject;
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new ConversionException("Deserialization failed", e);
        }
        return objects;
    }

    @Override
    public Object convert(Object arg, Type type) throws ConversionException {
        return objectMapper.convertValue(arg, objectMapper.constructType(type));
    }

}
