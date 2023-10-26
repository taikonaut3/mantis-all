package io.github.astro.mantis.configuration.extension;

import io.github.astro.mantis.common.constant.Key;
import io.github.astro.mantis.configuration.MantisBootStrap;

import java.util.HashMap;
import java.util.Map;

public class MantisContext {
    private static final ThreadLocal<MantisContext> threadLocal = new ThreadLocal<>();

    private Map<String, Object> contextMap;

    private MantisContext() {
        contextMap = new HashMap<>();
    }

    public static MantisContext getContext() {
        MantisContext context = threadLocal.get();
        if (context == null) {
            context = new MantisContext();
            threadLocal.set(context);
        }
        return context;
    }

    public static MantisBootStrap getCurrentBootStrap() {
        return (MantisBootStrap) getContext().get(Key.MANTIS_BOOTSTRAP);
    }

    public static void setCurrentBootStrap(MantisBootStrap mantisBootStrap) {
        getContext().set(Key.MANTIS_BOOTSTRAP, mantisBootStrap);
    }

    public MantisContext set(String key, Object value) {
        contextMap.put(key, value);
        return this;
    }

    public Object get(String key) {
        return contextMap.get(key);
    }

    public <T> T get(String key, Class<T> type) {
        Object value = contextMap.get(key);
        if (value != null) {
            return type.cast(value);
        }
        return null;
    }

    public MantisContext remove(String key) {
        contextMap.remove(key);
        return this;
    }

    public MantisContext clear() {
        contextMap.clear();
        return this;
    }

    public Map<String, Object> getAll() {
        return new HashMap<>(contextMap);
    }
}
