package io.github.astro.mantis.configuration.extension.spi;

import io.github.astro.mantis.common.exception.SourceException;

import java.util.List;
import java.util.Map;
import java.util.ServiceLoader;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class ServiceProviderLoader<S> {

    private static final Map<Class<?>, ServiceLoader<?>> LOADERMAP = new ConcurrentHashMap<>();

    public static <S> S loadService(Class<S> interfaceType, String name) {
        ServiceLoader<S> loader = load(interfaceType);
        for (S service : loader) {
            ServiceProvider provider = service.getClass().getAnnotation(ServiceProvider.class);
            if (provider.value().equals(name)) {
                return service;
            }
        }
        throw new SourceException("Can't find " + interfaceType.getSimpleName() + " named " + name);
    }

    public static <S> S loadService(Class<S> interfaceType) {
        ServiceInterface serviceInterface = interfaceType.getAnnotation(ServiceInterface.class);
        return loadService(interfaceType, serviceInterface.value());
    }

    public static <S> List<S> loadServices(Class<S> interfaceType) {
        ServiceLoader<S> loader = load(interfaceType);
        return loader.stream()
                .map(ServiceLoader.Provider::get)
                .collect(Collectors.toList());
    }

    @SuppressWarnings("unchecked")
    public static <S> ServiceLoader<S> load(Class<S> interfaceType) {
        ServiceLoader<?> loader = LOADERMAP.get(interfaceType);
        if (loader == null) {
            loader = ServiceLoader.load(interfaceType);
            LOADERMAP.put(interfaceType, loader);
        }
        return (ServiceLoader<S>) loader;
    }
}
