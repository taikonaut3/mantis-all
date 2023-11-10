package io.github.astro.mantis.serialization;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author WenBo Zhou
 * @Date 2023/5/5 10:14
 */
public class SerializationOptimizer {

    private final Map<String, Class<?>> serializationMap;

    public SerializationOptimizer() {
        this.serializationMap = new HashMap<>();
    }

    public void registerClass(String className, Class<?> clazz) {
        serializationMap.put(className, clazz);
    }

    public Class<?> getClass(String className) {
        return serializationMap.get(className);
    }

}
