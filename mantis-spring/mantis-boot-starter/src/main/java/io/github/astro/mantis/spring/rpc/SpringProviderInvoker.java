package io.github.astro.mantis.spring.rpc;

import io.github.astro.mantis.common.constant.Key;
import io.github.astro.mantis.common.util.NetUtils;
import io.github.astro.mantis.configuration.Exporter;
import io.github.astro.mantis.configuration.ExporterURL;
import io.github.astro.mantis.configuration.ProtocolConfig;
import io.github.astro.mantis.rpc.configuration.DefaultProviderInvoker;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class SpringProviderInvoker extends DefaultProviderInvoker {

    public SpringProviderInvoker(Method method, Exporter<?> exporter) {
        super(method, exporter);
    }

    @Override
    protected List<ExporterURL> createExporterUrls() {
        ArrayList<ExporterURL> urls = new ArrayList<>();
        String ipAddress = NetUtils.getLocalHost();
        for (ProtocolConfig protocolConfig : getProtocolConfigs()) {
            String address = NetUtils.getAddress(ipAddress, protocolConfig.getPort());
            ExporterURL exporterURL = new ExporterURL(protocolConfig.getType().getName(), address);
            exporterURL.setApplicationName(getApplicationName());
            exporterURL.setExportName(getExportName());
            exporterURL.setMethodKey(getMethodKey());
            exporterURL.addParameter(Key.CLASS, getExporter().getTarget().getClass().getName());
            exporterURL.addParameters(parameterization());
            urls.add(exporterURL);
        }
        return urls;
    }
}
