package io.github.astro.mantis.configuration;

import io.github.astro.mantis.common.constant.Key;
import io.github.astro.mantis.common.constant.Mode;
import io.github.astro.mantis.common.constant.ModeContainer;
import io.github.astro.mantis.common.util.CollectionUtils;
import io.github.astro.mantis.configuration.annotation.Option;
import io.github.astro.mantis.configuration.annotation.Parameter;
import io.github.astro.mantis.configuration.config.ProtocolConfig;
import io.github.astro.mantis.configuration.config.RegistryConfig;
import io.github.astro.mantis.configuration.manager.ConfigurationManager;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

@Accessors(chain = true)
@Getter
public abstract class AbstractCaller extends ParseAnnotationLifecycle implements Caller {

    protected CallerContainer callerContainer;

    protected String applicationName;

    protected String remoteServiceName;

    protected String callName;

    protected Method method;

    @Setter
    @Parameter(Key.GROUP)
    private String group;

    @Setter
    @Parameter(Key.VERSION)
    private String version;

    @Setter
    @Parameter(Key.SERIALIZE)
    private String serialize;

    @Setter
    @Parameter(Key.TRANSPORT)
    private String transport;

    @Setter
    @Parameter(Key.EVENT_DISPATCHER)
    private String eventDispatcher;

    private List<CallInterceptor> callInterceptors = new ArrayList<>();

    private List<RegistryConfig> registryConfigs = new ArrayList<>();

    private List<ProtocolConfig> protocolConfigs = new ArrayList<>();

    protected AbstractCaller() {

    }

    protected AbstractCaller(Method method, CallerContainer callerContainer) {
        this.method = method;
        this.callerContainer = callerContainer;
        init();
    }

    protected void parseOption(Option option, MantisApplication mantisApplication) {
        setTransport(option.transport());
        setSerialize(option.serialize());
        setEventDispatcher(option.eventDispatcher());
        parseConfig(mantisApplication.getConfigurationManager(), option.registries(), option.interceptors(), option.protocols());
    }

    @Override
    public Mode getSerialize() {
        return ModeContainer.getMode(Key.SERIALIZE, serialize);
    }

    @Override
    public Type getReturnType() {
        return method.getGenericReturnType();
    }

    @Override
    public void addCallInterceptor(CallInterceptor... interceptors) {
        CollectionUtils.addToList(callInterceptors,
                (oldInterceptor, newInterceptor) -> oldInterceptor == newInterceptor,
                interceptors);
    }

    @Override
    public void addRegistryConfig(RegistryConfig... configs) {
        CollectionUtils.addToList(registryConfigs,
                (registryConfig, config) ->
                        config.getType() == registryConfig.getType() &&
                                config.getHost().equals(registryConfig.getHost()) &&
                                config.getPort() == registryConfig.getPort(),
                configs);
    }

    @Override
    public void addProtocolConfig(ProtocolConfig... configs) {
        CollectionUtils.addToList(protocolConfigs,
                (protocolConfig, config) ->
                        protocolConfig.getType() == config.getType() && protocolConfig.getPort() == config.getPort(),
                configs);
    }

    private void parseConfig(ConfigurationManager manager, String[] registryNames, String[] interceptorNames, String[] protocolNames) {
        for (RegistryConfig registryConfig : manager.getRegistryManager().getApplicationScopeConfigs()) {
            addRegistryConfig(registryConfig);
        }
        for (ProtocolConfig protocolConfig : manager.getProtocolManager().getApplicationScopeConfigs()) {
            addProtocolConfig(protocolConfig);
        }
        if (registryNames != null) {
            for (String registryName : registryNames) {
                RegistryConfig registryConfig = manager.getRegistryManager().get(registryName);
                if (registryConfig != null) {
                    if (!registryConfig.isEnabled()) {
                        registryConfig.setEnabled(true);
                    }
                    addRegistryConfig(registryConfig);
                }
            }
        }
        if (interceptorNames != null) {
            for (String interceptorName : interceptorNames) {
                CallInterceptor interceptor = manager.getInterceptorManager().get(interceptorName);
                if (interceptor != null) {
                    addCallInterceptor(interceptor);
                }
            }
        }
        if (protocolNames != null) {
            for (String protocolName : protocolNames) {
                ProtocolConfig protocolConfig = manager.getProtocolManager().get(protocolName);
                if (protocolConfig != null) {
                    addProtocolConfig(protocolConfig);
                }
            }
        }
    }

    @Override
    public String toString() {
        return getApplicationName() + "." + getRemoteServiceName() + "." + getCallName();
    }

}
