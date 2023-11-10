package io.github.astro.mantis.configuration.manager;

import io.github.astro.mantis.common.constant.ConfigScope;
import io.github.astro.mantis.configuration.config.ProtocolConfig;

import java.util.List;
import java.util.stream.Collectors;

public class ProtocolManager extends AbstractManager<ProtocolConfig> {

    public List<ProtocolConfig> getApplicationScopeConfigs() {
        return getManagerMap().values().stream().
                filter(protocolConfig -> protocolConfig.getScope() == ConfigScope.APPLICATION)
                .collect(Collectors.toList());
    }

    public void register(ProtocolConfig protocolConfig) {
        register(protocolConfig.getName(), protocolConfig);
    }

}
