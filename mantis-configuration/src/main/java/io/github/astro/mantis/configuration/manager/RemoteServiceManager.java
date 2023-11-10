package io.github.astro.mantis.configuration.manager;

import io.github.astro.mantis.configuration.RemoteService;

import java.util.Collection;

public class RemoteServiceManager extends AbstractManager<RemoteService<?>> {

    public Collection<RemoteService<?>> getRemoteService() {
        return map.values();
    }

    public void register(RemoteService<?> remoteService) {
        register(remoteService.getRemoteServiceName(), remoteService);
    }

}
