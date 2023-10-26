package io.github.astro.mantis.configuration.manager;

import io.github.astro.mantis.common.util.CollectionUtils;
import io.github.astro.mantis.configuration.Exporter;
import io.github.astro.mantis.configuration.RemoteCaller;

import java.util.ArrayList;
import java.util.List;

public class MantisMonitorManager {

    private List<Exporter<?>> exporters = new ArrayList<>();

    private List<RemoteCaller<?>> remoteCallers = new ArrayList<>();

    public void addProvider(Exporter<?>... exporters) {
        CollectionUtils.addToList(this.exporters,
                (oldExporter, newExporter) ->
                        oldExporter.getApplicationName().equals(newExporter.getApplicationName()) &&
                                oldExporter.getExportName().equals(newExporter.getExportName())
                , exporters);
    }

    public List<Exporter<?>> getProviders() {
        return exporters;
    }

    public void addConsumer(RemoteCaller<?>... remoteCallers) {
        CollectionUtils.addToList(this.remoteCallers,
                (oldCaller, newCaller) ->
                        oldCaller.getApplicationName().equals(newCaller.getApplicationName())
                , remoteCallers);
    }

    public List<RemoteCaller<?>> getConsumers() {
        return remoteCallers;
    }
}
