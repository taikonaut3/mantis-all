package io.github.astro.mantis.spring.cloud.configuration;

import io.github.astro.mantis.common.constant.Key;
import io.github.astro.mantis.common.util.StringUtils;
import io.github.astro.mantis.configuration.ConsumerInvoker;
import io.github.astro.mantis.configuration.ExporterURL;
import io.github.astro.mantis.configuration.URL;
import io.github.astro.mantis.configuration.extension.spi.ServiceProvider;
import io.github.astro.mantis.configuration.invoke.Invocation;
import io.github.astro.mantis.configuration.util.GenerateUtil;
import io.github.astro.mantis.governance.directory.Directory;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.context.ApplicationContext;

import java.util.List;
import java.util.Objects;

import static io.github.astro.mantis.common.constant.ServiceType.SPRING;

@ServiceProvider(SPRING)
public class SpringDiscoveryDirectory implements Directory {

    private ApplicationContext applicationContext;

    private DiscoveryClient discoveryClient;

    public void setApplicationContext(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
        discoveryClient = applicationContext.getBean(DiscoveryClient.class);
    }

    @Override
    public List<URL> list(Invocation invocation, URL... urls) {
        List<ServiceInstance> instances = discoveryClient.getInstances(invocation.getApplicationName());
        ConsumerInvoker invoker = (ConsumerInvoker) invocation.getInvoker();
        return instances.stream().map(serviceInstance -> {
            String protocolUrlStr = serviceInstance.getMetadata().get(GenerateUtil.generateMetaProtocolKey(invoker.getProtocol()));
            String wight = serviceInstance.getMetadata().get(Key.REGISTRY_META_WEIGHT);
            if (StringUtils.isBlank(protocolUrlStr)) {
                return null;
            }
            URL protocolUrl = URL.valueOf(protocolUrlStr);
            ExporterURL url = new ExporterURL(invoker.getProtocol().getName(), protocolUrl.getAddress());
            url.setApplicationName(invoker.getApplicationName());
            url.setExportName(invoker.getExportName());
            url.setMethodKey(invoker.getMethodKey());
            url.addParameter(Key.WEIGHT, wight);
            return (URL) url;
        }).filter(Objects::nonNull).toList();
    }

    @Override
    public void destroy() {

    }
}
