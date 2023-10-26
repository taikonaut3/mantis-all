package io.github.astro.mantis.configuration.extension;

import java.util.Map;

public interface Accessor<V> {

    void setData(Map<String, V> params);

    void replaceAccessor(Map<String, V> params);

    void setData(String key, V value);

    V getData(String key);

    V getData(String key, V defaultValue);

    Map<String, V> getAccessor();
}
