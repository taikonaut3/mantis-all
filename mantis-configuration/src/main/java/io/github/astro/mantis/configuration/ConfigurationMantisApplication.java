package io.github.astro.mantis.configuration;

import io.github.astro.mantis.configuration.spi.ServiceInterface;

@ServiceInterface
public interface ConfigurationMantisApplication {

    default void configureInit(MantisApplication application) {

    }

    default void configureStart(MantisApplication application) {

    }

    default void configureStop(MantisApplication application) {
    }

}
