package io.github.astro.mantis.monitor;

import io.github.astro.mantis.configuration.MantisApplication;
import io.github.astro.mantis.configuration.RemoteCaller;
import io.github.astro.mantis.monitor.response.RemoteServiceResponse;

import java.util.List;
import java.util.stream.Collectors;

public class MonitorRemoteService implements MonitorBridge {

    private final MantisApplication mantisApplication;

    public MonitorRemoteService(MantisApplication mantisApplication) {
        this.mantisApplication = mantisApplication;
    }

    @Override
    public List<RemoteServiceResponse> getRemoteServices() {
        return mantisApplication.getMonitorManager().getProviders().stream().map(RemoteServiceResponse::new).collect(Collectors.toList());
    }

    @Override
    public List<RemoteCaller<?>> getRemoteCallers() {
        return mantisApplication.getMonitorManager().getConsumers();
    }

}
