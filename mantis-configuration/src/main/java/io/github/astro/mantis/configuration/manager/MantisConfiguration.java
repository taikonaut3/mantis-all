package io.github.astro.mantis.configuration.manager;

import io.github.astro.mantis.configuration.extension.spi.ServiceInterface;

@ServiceInterface
public interface MantisConfiguration {
    void configure(ConfigurationManager manager);
}
