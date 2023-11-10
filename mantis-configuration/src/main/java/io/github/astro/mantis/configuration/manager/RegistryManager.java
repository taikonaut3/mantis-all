package io.github.astro.mantis.configuration.manager;

import io.github.astro.mantis.common.constant.ConfigScope;
import io.github.astro.mantis.configuration.config.RegistryConfig;

import java.util.List;
import java.util.stream.Collectors;

public class RegistryManager extends AbstractManager<RegistryConfig> {

    public List<RegistryConfig> getApplicationScopeConfigs() {
        return getManagerMap().values().stream().
                filter(protocolConfig -> protocolConfig.getScope() == ConfigScope.APPLICATION)
                .collect(Collectors.toList());
    }

    public void register(RegistryConfig registryConfig) {
        register(registryConfig.getName(), registryConfig);
    }

}
