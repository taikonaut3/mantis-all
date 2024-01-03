package io.github.astro.mantis.configuration;

import io.github.astro.mantis.configuration.config.ApplicationConfig;
import io.github.astro.mantis.configuration.config.ProtocolConfig;
import io.github.astro.mantis.configuration.config.RegistryConfig;
import io.github.astro.mantis.configuration.manager.ConfigurationManager;
import io.github.astro.mantis.configuration.manager.MantisMonitorManager;
import io.github.astro.mantis.configuration.spi.ExtensionLoader;
import lombok.Getter;

import java.util.List;

/**
 * Mantis application configuration
 * manage each configuration component
 */
@Getter
public class MantisApplication implements Lifecycle {

    private final ConfigurationManager configurationManager;

    private ApplicationConfig appConfiguration;

    private MantisMonitorManager monitorManager;

    private List<ConfigurationMantisApplication> configurations;

    public MantisApplication(ConfigurationManager configurationManager) {
        this.configurationManager = configurationManager;
    }

    public MantisApplication() {
        configurationManager = new ConfigurationManager();
        monitorManager = new MantisMonitorManager();
        appConfiguration = new ApplicationConfig();
        configurations = ExtensionLoader.loadServices(ConfigurationMantisApplication.class);
    }

    public void setApplicationName(String applicationName){
        appConfiguration.setApplicationName(applicationName);
    }

    public String getApplicationName(){
        return appConfiguration.getApplicationName();
    }

    public void addRemoteService(RemoteService<?>... remoteServices) {
        for (RemoteService<?> remoteService : remoteServices) {
            configurationManager.getRemoteServiceManager().register(remoteService);
        }
    }

    public void addRemoteCaller(RemoteCaller<?>... remoteCallers) {
        for (RemoteCaller<?> remoteCaller : remoteCallers) {
            configurationManager.getRemoteCallerManager().register(remoteCaller);
        }
    }

    public void addProtocolConfig(ProtocolConfig... configs) {
        for (ProtocolConfig config : configs) {
            configurationManager.getProtocolManager().register(config);
        }
    }

    public void addRegistryConfig(RegistryConfig... configs) {
        for (RegistryConfig config : configs) {
            configurationManager.getRegistryManager().register(config.getName(), config);
        }
    }

    public void addProcessor(String name, CallInterceptor interceptor) {
        configurationManager.getInterceptorManager().register(name, interceptor);
    }

    @Override
    public void init() {
        for (ConfigurationMantisApplication configuration : configurations) {
            configuration.configureInit(this);
        }
    }

    public synchronized void start() {
        for (ConfigurationMantisApplication configuration : configurations) {
            configuration.configureStart(this);
        }

        for (RemoteService<?> remoteService : getConfigurationManager().getRemoteServiceManager().getRemoteService()) {
            remoteService.start();
        }

        for (RemoteCaller<?> remoteCaller : getConfigurationManager().getRemoteCallerManager().getRemoteCallers()) {
            remoteCaller.start();
        }
    }

    @Override
    public void stop() {
        for (ConfigurationMantisApplication configuration : configurations) {
            configuration.configureStop(this);
        }
    }

}
