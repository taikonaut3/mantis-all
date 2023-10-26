package io.github.astro.mantis.configuration;

import io.github.astro.mantis.common.constant.Key;
import io.github.astro.mantis.common.util.NetUtils;
import lombok.Getter;

import java.net.InetSocketAddress;
import java.util.Map;

@Getter
public class ExporterURL extends URL implements Source {

    private String applicationName;

    private String exportName;

    private String methodKey;

    private String description;

    public ExporterURL(String protocol, String address) {
        super(protocol, address);
    }

    public ExporterURL(String protocol, String address, String applicationName, String exportName, Map<String, String> params) {
        this(protocol, NetUtils.toInetSocketAddress(address), applicationName, exportName, params);
    }

    public ExporterURL(String protocol, String host, int port, String applicationName, String exportName, Map<String, String> params) {
        this(protocol, new InetSocketAddress(host, port), applicationName, exportName, params);
    }

    public ExporterURL(String protocol, InetSocketAddress address, String applicationName, String exportName, Map<String, String> params) {
        super(protocol, address, params);
        this.applicationName = applicationName;
        this.exportName = exportName;
        super.addPath(applicationName);
        super.addPath(exportName);
    }

    public void setDescription(String description) {
        this.description = description;
        super.addParameter(Key.DESCRIPTION, description);
    }

    @Override
    public void setApplicationName(String applicationName) {
        this.applicationName = applicationName;
        removePath(0);
        super.addPath(applicationName);
    }

    @Override
    public void setExportName(String exportName) {
        this.exportName = exportName;
        removePath(1);
        super.addPath(exportName);
    }

    public void setMethodKey(String methodKey) {
        this.methodKey = methodKey;
        removePath(2);
        super.addPath(methodKey);
    }

    @Override
    public void addPath(String path) {
        // Not allow
    }

    @Override
    public void addPath(int index, String path) {
        // Not allow
    }
}
