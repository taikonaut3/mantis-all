package io.github.astro.mantis.configuration;

import io.github.astro.mantis.common.constant.Mode;
import io.github.astro.mantis.configuration.config.ProtocolConfig;
import io.github.astro.mantis.configuration.config.RegistryConfig;

import java.util.List;

public interface Options extends Parameterization {

    String getGroup();

    String getVersion();

    String getTransport();

    Mode getSerialize();

    String getEventDispatcher();

    void addCallInterceptor(CallInterceptor... interceptors);

    List<CallInterceptor> getCallInterceptors();

    void addRegistryConfig(RegistryConfig... configs);

    List<RegistryConfig> getRegistryConfigs();

    void addProtocolConfig(ProtocolConfig... configs);

    List<ProtocolConfig> getProtocolConfigs();

}
