package io.github.astro.mantis.spring.boot;

import io.github.astro.mantis.common.constant.ConfigScope;
import io.github.astro.mantis.common.util.StringUtils;
import io.github.astro.mantis.configuration.MantisApplication;
import io.github.astro.mantis.configuration.config.ProtocolConfig;
import io.github.astro.mantis.configuration.config.RegistryConfig;
import jakarta.annotation.Resource;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;
import java.util.stream.Collectors;

@ConfigurationProperties(prefix = "mantis")
public class MantisConfigurationProperties {

    @Resource
    private MantisApplication mantisApplication;

    public MantisConfigurationProperties() {

    }

    public List<ProtocolConfig> getProtocols() {
        return mantisApplication.getConfigurationManager().getProtocolManager().getManagerMap().values().stream().
                filter(protocolConfig -> protocolConfig.getScope() == ConfigScope.APPLICATION)
                .collect(Collectors.toList());
    }

    public void setProtocols(List<ProtocolConfig> globalProtocols) {
        for (ProtocolConfig configuration : globalProtocols) {
            if (StringUtils.isBlank(configuration.getName())) {
                configuration.setName("Global-" + configuration.toUrl().getAuthority());
            }
            configuration.setScope(ConfigScope.APPLICATION);
            mantisApplication.addProtocolConfig(configuration);
        }
    }

    public List<RegistryConfig> getRegistries() {
        return mantisApplication.getConfigurationManager().getRegistryManager().getManagerMap().values().stream().
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
            mantisApplication.addRegistryConfig(configuration);
        }
    }

    public int getWeight() {
        return mantisApplication.getAppConfiguration().getWeight();
    }

    public void setWeight(int weight) {
        mantisApplication.getAppConfiguration().setWeight(weight);
    }

    public void setEventDispatcher(String eventDispatcher) {
        mantisApplication.getAppConfiguration().setEventDispatcher(eventDispatcher);
    }

    public String getEventDispatcher() {
        return mantisApplication.getAppConfiguration().getEventDispatcher();
    }

    public String getApplicationName() {
        return mantisApplication.getAppConfiguration().getApplicationName();
    }

    public void setApplicationName(String applicationName) {
        mantisApplication.getAppConfiguration().setApplicationName(applicationName);
    }

    public MantisApplication getMantisBootStrap() {
        return mantisApplication;
    }

}
