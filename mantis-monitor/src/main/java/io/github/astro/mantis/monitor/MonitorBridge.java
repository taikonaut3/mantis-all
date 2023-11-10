package io.github.astro.mantis.monitor;

import io.github.astro.mantis.configuration.RemoteCaller;
import io.github.astro.mantis.monitor.response.RemoteServiceResponse;

import java.util.List;

public interface MonitorBridge {

    List<RemoteServiceResponse> getRemoteServices();

    List<RemoteCaller<?>> getRemoteCallers();

}
