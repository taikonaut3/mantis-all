package io.github.astro.mantis.configuration;

import io.github.astro.mantis.configuration.base.Options;

import java.util.List;

public interface ProviderInvoker extends Invoker, Options {

    Exporter<?> getExporter();

    void export();

    List<ExporterURL> getExporterUrls();

    void addProtocolConfig(ProtocolConfig... configs);

    ProtocolConfig[] getProtocolConfigs();

}
