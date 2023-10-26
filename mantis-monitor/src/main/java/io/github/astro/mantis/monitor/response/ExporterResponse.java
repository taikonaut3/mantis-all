package io.github.astro.mantis.monitor.response;

import io.github.astro.mantis.configuration.Exporter;
import io.github.astro.mantis.configuration.Invoker;
import io.github.astro.mantis.configuration.ProviderInvoker;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author WenBo Zhou
 * @Date 2023/10/13 14:55
 */
@Getter
@Setter
public class ExporterResponse {

    private String applicationName;
    private String exporterName;
    private String className;

    private List<ProviderInvokerResponse> providerInvokers = new ArrayList<>();

    public ExporterResponse() {

    }

    public ExporterResponse(Exporter<?> exporter) {
        exporterName = exporter.getExportName();
        applicationName = exporter.getApplicationName();
        className = exporter.getTarget().getClass().getName();
        for (Invoker invoker : exporter.getInvokers()) {
            ProviderInvoker providerInvoker = (ProviderInvoker) invoker;
            providerInvokers.add(new ProviderInvokerResponse(providerInvoker));
        }
    }
}
