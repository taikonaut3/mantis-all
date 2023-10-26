package io.github.astro.mantis.monitor;

import io.github.astro.mantis.common.constant.Constant;
import io.github.astro.mantis.configuration.MantisBootStrap;
import io.github.astro.mantis.configuration.RemoteCaller;
import io.github.astro.mantis.configuration.annotation.Export;
import io.github.astro.mantis.configuration.annotation.Procedure;
import io.github.astro.mantis.monitor.response.ExporterResponse;

import java.util.List;
import java.util.stream.Collectors;

@Export(Constant.MONITOR_EXPORTER_NAME)
public class MonitorExporter implements MonitorBridge {

    private final MantisBootStrap mantisBootStrap;

    public MonitorExporter(MantisBootStrap mantisBootStrap) {
        this.mantisBootStrap = mantisBootStrap;
    }

    @Override
    @Procedure
    public List<ExporterResponse> getExporters() {
        return mantisBootStrap.getMonitorManager().getProviders().stream().map(ExporterResponse::new).collect(Collectors.toList());
    }

    @Override
    @Procedure
    public List<RemoteCaller<?>> getRemoteCallers() {
        return mantisBootStrap.getMonitorManager().getConsumers();
    }
}
