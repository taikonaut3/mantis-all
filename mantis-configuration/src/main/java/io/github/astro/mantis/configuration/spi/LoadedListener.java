package io.github.astro.mantis.configuration.spi;

@FunctionalInterface
public interface LoadedListener<T> {

    void listen(T service);

}
