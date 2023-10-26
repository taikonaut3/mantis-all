package io.github.astro.mantis.configuration;

import io.github.astro.mantis.configuration.manager.ApplicationConfiguration;
import io.github.astro.mantis.configuration.manager.ConfigurationManager;
import io.github.astro.mantis.configuration.manager.MantisMonitorManager;
import lombok.Getter;

import java.util.Collection;

/**
 * Mantis application configuration
 * manage each configuration component
 */
@Getter
public class MantisBootStrap {

    private final ConfigurationManager configurationManager;
    private ApplicationConfiguration appConfiguration;
    private MantisMonitorManager monitorManager;

    public MantisBootStrap(ConfigurationManager configurationManager) {
        this.configurationManager = configurationManager;
    }

    public MantisBootStrap() {
        configurationManager = ConfigurationManager.defaultManager();
        monitorManager = new MantisMonitorManager();
        appConfiguration = new ApplicationConfiguration();
    }

    public void addExporter(Exporter<?>... exporters) {
        for (Exporter<?> exporter : exporters) {
            configurationManager.getExporterManager().register(exporter.getExportName(), exporter);
        }
    }

    public void addRemoteCaller(RemoteCaller<?>... remoteCallers) {
        for (RemoteCaller<?> remoteCaller : remoteCallers) {
            configurationManager.getRemoteCallerManager().register(remoteCaller.getApplicationName(), remoteCaller);
        }
    }

    public void addProtocolConfig(ProtocolConfig... configs) {
        for (ProtocolConfig config : configs) {
            configurationManager.getProtocolManager().register(config.getName(), config);
        }
    }

    public void addRegistryConfig(RegistryConfig... configs) {
        for (RegistryConfig config : configs) {
            configurationManager.getRegistryManager().register(config.getName(), config);
        }
    }

    public void addProcessor(String name, InvokerProcessor processor) {
        configurationManager.getProcessorManager().register(name, processor);
    }

    public String getApplicationName() {
        return appConfiguration.getApplicationName();
    }

    public void setApplicationName(String applicationName) {
        appConfiguration.setApplicationName(applicationName);
    }

    public int getWeight() {
        return appConfiguration.getWeight();
    }

    public void setWeight(int weight) {
        appConfiguration.setWeight(weight);
    }

    public synchronized void start() {
        Collection<Exporter<?>> exporters = configurationManager.getExporterManager().getExports();
        for (Exporter<?> exporter : exporters) {
            exporter.export();
            monitorManager.addProvider(exporter);
        }
    }

}
