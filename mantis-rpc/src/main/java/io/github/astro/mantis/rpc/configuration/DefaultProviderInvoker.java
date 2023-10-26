package io.github.astro.mantis.rpc.configuration;

import io.github.astro.mantis.common.constant.Key;
import io.github.astro.mantis.common.util.AssertUtils;
import io.github.astro.mantis.common.util.CollectionUtils;
import io.github.astro.mantis.common.util.NetUtils;
import io.github.astro.mantis.common.util.StringUtils;
import io.github.astro.mantis.configuration.*;
import io.github.astro.mantis.configuration.annotation.Procedure;
import io.github.astro.mantis.configuration.base.AbstractOptions;
import io.github.astro.mantis.configuration.extension.spi.ServiceProviderLoader;
import io.github.astro.mantis.configuration.invoke.Invocation;
import io.github.astro.mantis.configuration.manager.ConfigurationManager;
import io.github.astro.mantis.configuration.util.GenerateUtil;
import io.github.astro.mantis.protocol.Protocol;
import io.github.astro.mantis.registry.Registry;
import io.github.astro.mantis.registry.RegistryFactory;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class DefaultProviderInvoker extends AbstractOptions implements ProviderInvoker {

    private static final Class<Procedure> PROCEDURECLASS = Procedure.class;
    private final List<ProtocolConfig> protocolConfigs = new ArrayList<>();
    private final Exporter<?> exporter;
    private final Method method;
    private volatile List<ExporterURL> urls;
    private String methodKey;

    public DefaultProviderInvoker(Method method, Exporter<?> exporter) {
        AssertUtils.assertCondition(method != null && exporter != null);
        AssertUtils.assertCondition(checkInvoke(method), "Only support @Procedure modify Method");
        this.exporter = exporter;
        this.method = method;
        // parse @Procedure
        parse();
    }

    private static boolean checkInvoke(Method method) {
        return method.isAnnotationPresent(PROCEDURECLASS);
    }

    @Override
    protected void parseConfig(ConfigurationManager manager, String[] registryNames, String[] processorNames) {
        super.parseConfig(manager, registryNames, processorNames);
        for (ProtocolConfig protocolConfig : manager.getProtocolManager().getApplicationScopeConfigs()) {
            addProtocolConfig(protocolConfig);
        }
        Procedure procedure = method.getAnnotation(PROCEDURECLASS);
        if (procedure.protocols() != null) {
            for (String protocol : procedure.protocols()) {
                ProtocolConfig protocolConfig = manager.getProtocolManager().get(protocol);
                if (protocolConfig != null) {
                    addProtocolConfig(protocolConfig);
                }
            }

        }
    }

    @Override
    protected void parseAnnotation() {
        Procedure procedure = method.getAnnotation(PROCEDURECLASS);
        String methodKey = procedure.value();
        if (!StringUtils.isBlank(methodKey)) {
            methodKey = procedure.value();
        } else {
            methodKey = GenerateUtil.generateKey(method);
        }
        this.setGroup(procedure.group()).setVersion(procedure.version())
                .setProxy(procedure.proxy()).setSerialize(procedure.serialize());
        this.setMethodKey(methodKey);
        this.setApplicationName(exporter.getApplicationName());
        this.setExportName(exporter.getExportName());
        parseConfig(exporter.getMantisBootStrap().getConfigurationManager(), procedure.registries(), procedure.processors());
        // 创建exporterUrl
        urls = createExporterUrls();
    }

    @Override
    public Object invoke(Invocation invocation) {
        try {
            method.setAccessible(true);
            return method.invoke(exporter.getTarget(), invocation.getArgs());
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e.getTargetException());
        }
    }

    @Override
    public List<ExporterURL> getExporterUrls() {
        return urls;
    }

    protected List<ExporterURL> createExporterUrls() {
        ArrayList<ExporterURL> urls = new ArrayList<>();
        for (ProtocolConfig protocolConfig : getProtocolConfigs()) {
            String address = NetUtils.getAddress(NetUtils.getLocalHost(), protocolConfig.getPort());
            ExporterURL exporterURL = new ExporterURL(protocolConfig.getType().getName(), address);
            exporterURL.setApplicationName(getApplicationName());
            exporterURL.setExportName(getExportName());
            exporterURL.setMethodKey(methodKey);
            exporterURL.addParameter(Key.CLASS, exporter.getTarget().getClass().getName());
            exporterURL.addParameters(parameterization());
            urls.add(exporterURL);
        }
        return urls;
    }

    @Override
    public void export() {
        // 如果配置了RegistryConfig，那么注册所有的 ExporterURL 至注册中心
        for (RegistryConfig registryConfig : getRegistryConfigs()) {
            URL url = registryConfig.toUrl();
            RegistryFactory registryFactory = ServiceProviderLoader.loadService(RegistryFactory.class, url.getProtocol());
            Registry registry = registryFactory.getRegistry(url);
            for (ExporterURL exporterURL : getExporterUrls()) {
                registry.register(exporterURL);
            }
        }
        // 开启协议端口
        for (ExporterURL exporterURL : getExporterUrls()) {
            Protocol protocol = ServiceProviderLoader.loadService(Protocol.class, exporterURL.getProtocol());
            protocol.openServer(exporterURL);
        }

    }

    @Override
    public MethodInvoker getMethodInvoker() {
        return exporter;
    }

    @Override
    public Method getMethod() {
        return method;
    }

    @Override
    public String getMethodKey() {
        return methodKey;
    }

    public void setMethodKey(String methodKey) {
        this.methodKey = methodKey;
    }

    @Override
    public Type getReturnType() {
        return method.getGenericReturnType();
    }

    @Override
    public Exporter<?> getExporter() {
        return exporter;
    }

    @Override
    public void addProtocolConfig(ProtocolConfig... configs) {
        CollectionUtils.addToList(protocolConfigs,
                (protocolConfig, config) ->
                        protocolConfig.getType() == config.getType() && protocolConfig.getPort() == config.getPort(),
                configs);
    }

    @Override
    public ProtocolConfig[] getProtocolConfigs() {
        return protocolConfigs.toArray(new ProtocolConfig[0]);
    }

    @Override
    public String getApplicationName() {
        return exporter.getApplicationName();
    }

    @Override
    public String getExportName() {
        return exporter.getExportName();
    }

    @Override
    public String toString() {
        return methodKey + ":" + method.toString();
    }
}
