package io.github.astro.mantis.registry;

import io.github.astro.mantis.configuration.URL;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public abstract class AbstractRegistryFactory implements RegistryFactory {

    private static final Map<String, Registry> registries = new ConcurrentHashMap<>();

    @Override
    public Registry getRegistry(URL url) {
        String uri = url.getAuthority();
        Registry registry = registries.get(uri);
        if (registry == null) {
            registry = create(url);
            registries.put(uri, registry);
        } else {
            if (!registry.isConnected()) {
                registry.connect(url);
            }
        }
        return registry;
    }

    protected abstract Registry create(URL url);

}
