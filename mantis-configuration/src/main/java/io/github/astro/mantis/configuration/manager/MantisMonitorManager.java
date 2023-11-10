package io.github.astro.mantis.configuration.manager;

import io.github.astro.mantis.common.util.CollectionUtils;
import io.github.astro.mantis.configuration.RemoteCaller;
import io.github.astro.mantis.configuration.RemoteService;

import java.util.ArrayList;
import java.util.List;

public class MantisMonitorManager {

    private List<RemoteService<?>> remoteServices = new ArrayList<>();

    private List<RemoteCaller<?>> remoteCallers = new ArrayList<>();

    public void addProvider(RemoteService<?>... remoteServices) {
        CollectionUtils.addToList(this.remoteServices,
                (oldRemoteService, newRemoteService) ->
                        oldRemoteService.getApplicationName().equals(newRemoteService.getApplicationName()) &&
                                newRemoteService.getRemoteServiceName().equals(newRemoteService.getRemoteServiceName())
                , remoteServices);
    }

    public List<RemoteService<?>> getProviders() {
        return remoteServices;
    }

    public void addConsumer(RemoteCaller<?>... remoteCallers) {
        CollectionUtils.addToList(this.remoteCallers,
                (oldCaller, newCaller) ->
                        oldCaller.getRemoteApplicationName().equals(newCaller.getRemoteApplicationName())
                , remoteCallers);
    }

    public List<RemoteCaller<?>> getConsumers() {
        return remoteCallers;
    }

}
