package io.github.astro.mantis.configuration.manager;

import io.github.astro.mantis.configuration.extension.spi.ServiceProviderLoader;
import lombok.Getter;

import java.util.List;

@Getter
public class ConfigurationManager {

    private ExporterManager exporterManager;

    private RemoteCallerManager remoteCallerManager;

    private ProtocolManager protocolManager;

    private RegistryManager registryManager;

    private ProcessorManager processorManager;

    private ConfigurationManager() {
        exporterManager = new ExporterManager();
        remoteCallerManager = new RemoteCallerManager();
        protocolManager = new ProtocolManager();
        registryManager = new RegistryManager();
        processorManager = new ProcessorManager();
        loadConfiguration();
    }

    public static ConfigurationManager defaultManager() {
        return ConfigurationManagerHolder.configurationManager;
    }

    private void loadConfiguration() {
        List<MantisConfiguration> configurations = ServiceProviderLoader.loadServices(MantisConfiguration.class);
        for (MantisConfiguration configuration : configurations) {
            configuration.configure(this);
        }
    }

    private static final class ConfigurationManagerHolder {
        private static final ConfigurationManager configurationManager = new ConfigurationManager();
    }
}
