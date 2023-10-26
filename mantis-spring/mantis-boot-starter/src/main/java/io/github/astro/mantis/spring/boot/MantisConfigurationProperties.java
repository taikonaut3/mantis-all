package io.github.astro.mantis.spring.boot;

import io.github.astro.mantis.common.constant.ConfigScope;
import io.github.astro.mantis.common.util.StringUtils;
import io.github.astro.mantis.configuration.MantisBootStrap;
import io.github.astro.mantis.configuration.ProtocolConfig;
import io.github.astro.mantis.configuration.RegistryConfig;
import jakarta.annotation.Resource;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;
import java.util.stream.Collectors;

@ConfigurationProperties(prefix = "mantis")
public class MantisConfigurationProperties {

    @Resource
    private MantisBootStrap mantisBootStrap;

    public MantisConfigurationProperties() {

    }

    public List<ProtocolConfig> getProtocols() {
        return mantisBootStrap.getConfigurationManager().getProtocolManager().getManagerMap().values().stream().
                filter(protocolConfig -> protocolConfig.getScope() == ConfigScope.APPLICATION)
                .collect(Collectors.toList());
    }

    public void setProtocols(List<ProtocolConfig> globalProtocols) {
        for (ProtocolConfig configuration : globalProtocols) {
            if (StringUtils.isBlank(configuration.getName())) {
                configuration.setName("Global-" + configuration.toUrl().getAuthority());
            }
            configuration.setScope(ConfigScope.APPLICATION);
            mantisBootStrap.addProtocolConfig(configuration);
        }
    }

    public List<RegistryConfig> getRegistries() {
        return mantisBootStrap.getConfigurationManager().getRegistryManager().getManagerMap().values().stream().
                filter(registryConfig -> registryConfig.getScope() == ConfigScope.APPLICATION)
                .collect(Collectors.toList());
    }

    public void setRegistries(List<RegistryConfig> globalRegistries) {
        for (RegistryConfig configuration : globalRegistries) {
            if (StringUtils.isBlank(configuration.getName())) {
                configuration.setName("Global-" + configuration.toUrl().getAuthority());
                configuration.setEnabled(true);
            }
            configuration.setScope(ConfigScope.APPLICATION);
            mantisBootStrap.addRegistryConfig(configuration);
        }
    }

    public int getWeight() {
        return mantisBootStrap.getWeight();
    }

    public void setWeight(int weight) {
        mantisBootStrap.setWeight(weight);
    }

    public String getApplicationName() {
        return mantisBootStrap.getApplicationName();
    }

    public void setApplicationName(String applicationName) {
        mantisBootStrap.setApplicationName(applicationName);
    }

    public MantisBootStrap getMantisBootStrap() {
        return mantisBootStrap;
    }

}
