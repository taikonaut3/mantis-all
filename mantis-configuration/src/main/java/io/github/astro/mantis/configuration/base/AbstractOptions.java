package io.github.astro.mantis.configuration.base;

import io.github.astro.mantis.common.constant.Key;
import io.github.astro.mantis.common.util.CollectionUtils;
import io.github.astro.mantis.configuration.InvokerProcessor;
import io.github.astro.mantis.configuration.RegistryConfig;
import io.github.astro.mantis.configuration.annotation.Parameter;
import io.github.astro.mantis.configuration.manager.ConfigurationManager;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Accessors(chain = true)
public abstract class AbstractOptions implements Options {

    @Setter
    @Getter
    @Parameter(Key.GROUP)
    private String group;

    @Setter
    @Getter
    @Parameter(Key.VERSION)
    private String version;

    @Setter
    @Getter
    @Parameter(Key.PROXY)
    private String proxy;

    @Setter
    @Getter
    @Parameter(Key.SERIALIZE)
    private String serialize;
    private List<InvokerProcessor> processors;

    private List<RegistryConfig> registryConfigs;

    public AbstractOptions() {
        this.processors = new ArrayList<>();
        this.registryConfigs = new ArrayList<>();
    }

    @Override
    public InvokerProcessor[] getProcessors() {
        return processors.toArray(new InvokerProcessor[0]);
    }

    @Override
    public RegistryConfig[] getRegistryConfigs() {
        return registryConfigs.toArray(new RegistryConfig[0]);
    }

    @Override
    public void addProcessor(InvokerProcessor... processors) {
        Collections.addAll(this.processors, processors);
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

    protected void parseConfig(ConfigurationManager manager, String[] registryNames, String[] processorNames) {
        for (RegistryConfig registryConfig : manager.getRegistryManager().getApplicationScopeConfigs()) {
            addRegistryConfig(registryConfig);
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
        if (processorNames != null) {
            for (String processorName : processorNames) {
                InvokerProcessor invokerProcessor = manager.getProcessorManager().get(processorName);
                if (invokerProcessor != null) {
                    addProcessor(invokerProcessor);
                }
            }
        }
    }

    protected void parse() {
        parseAnnotation();
        parseAnnotationAfter();
    }

    /**
     * Parse the annotation
     *
     * @see io.github.astro.mantis.configuration.annotation.Invoke,io.github.astro.mantis.configuration.ProviderInvoker
     * @see io.github.astro.mantis.configuration.annotation.Procedure,io.github.astro.mantis.configuration.ConsumerInvoker
     */
    protected abstract void parseAnnotation();

    /**
     * After invoke
     *
     * @see #parseAnnotation()
     */
    protected void parseAnnotationAfter() {

    }

    ;
}
