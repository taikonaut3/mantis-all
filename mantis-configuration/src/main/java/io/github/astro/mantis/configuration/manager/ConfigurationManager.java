package io.github.astro.mantis.configuration.manager;

import lombok.Getter;

@Getter
public class ConfigurationManager {

    private final RemoteServiceManager remoteServiceManager;

    private final RemoteCallerManager remoteCallerManager;

    private final ProtocolManager protocolManager;

    private final RegistryManager registryManager;

    private final CallInterceptorManager interceptorManager;

    public ConfigurationManager() {
        remoteServiceManager = new RemoteServiceManager();
        remoteCallerManager = new RemoteCallerManager();
        protocolManager = new ProtocolManager();
        registryManager = new RegistryManager();
        interceptorManager = new CallInterceptorManager();
    }

}
