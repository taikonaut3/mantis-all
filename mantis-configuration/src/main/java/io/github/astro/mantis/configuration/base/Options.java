package io.github.astro.mantis.configuration.base;

import io.github.astro.mantis.configuration.InvokerProcessor;
import io.github.astro.mantis.configuration.Parameterization;
import io.github.astro.mantis.configuration.RegistryConfig;

public interface Options extends Parameterization {

    String getGroup();

    String getVersion();

    String getProxy();

    String getSerialize();

    void addProcessor(InvokerProcessor... processors);

    InvokerProcessor[] getProcessors();

    void addRegistryConfig(RegistryConfig... configs);

    RegistryConfig[] getRegistryConfigs();
}
