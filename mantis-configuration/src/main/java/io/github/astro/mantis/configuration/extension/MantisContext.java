package io.github.astro.mantis.configuration.extension;

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

    public void clear() {
        contextMap.clear();
    }

    public Map<String, Object> getAll() {
        return new HashMap<>(contextMap);
    }

}
