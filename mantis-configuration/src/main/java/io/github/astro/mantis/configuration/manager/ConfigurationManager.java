package io.github.astro.mantis.configuration.manager;

import lombok.Getter;

@Getter
public class ConfigurationManager {

    private RemoteServiceManager remoteServiceManager;

    private RemoteCallerManager remoteCallerManager;

    private ProtocolManager protocolManager;

    private RegistryManager registryManager;

    private CallInterceptorManager interceptorManager;

    public ConfigurationManager() {
        remoteServiceManager = new RemoteServiceManager();
        remoteCallerManager = new RemoteCallerManager();
        protocolManager = new ProtocolManager();
        registryManager = new RegistryManager();
        interceptorManager = new CallInterceptorManager();
    }

}
