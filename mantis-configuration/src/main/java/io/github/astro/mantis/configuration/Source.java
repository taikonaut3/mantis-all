package io.github.astro.mantis.configuration;

public interface Source {

    String getApplicationName();

    default void setApplicationName(String applicationName) {
    }

    String getExportName();

    default void setExportName(String exportName) {
    }

}
