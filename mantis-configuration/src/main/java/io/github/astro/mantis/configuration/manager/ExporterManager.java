package io.github.astro.mantis.configuration.manager;

import io.github.astro.mantis.configuration.Exporter;

import java.util.Collection;

public class ExporterManager extends AbstractManager<Exporter<?>> {

    public Collection<Exporter<?>> getExports() {
        return map.values();
    }
}
