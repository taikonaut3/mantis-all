package io.github.astro.mantis.monitor;

import io.github.astro.mantis.common.constant.Constant;
import io.github.astro.mantis.configuration.RemoteCaller;
import io.github.astro.mantis.configuration.annotation.Invoke;
import io.github.astro.mantis.configuration.annotation.RemoteCall;
import io.github.astro.mantis.monitor.response.ExporterResponse;

import java.util.List;

@RemoteCall("")
public interface MonitorBridge {

    @Invoke(Constant.MONITOR_EXPORTER_NAME)
    List<ExporterResponse> getExporters();

    @Invoke(Constant.MONITOR_EXPORTER_NAME)
    List<RemoteCaller<?>> getRemoteCallers();
}
