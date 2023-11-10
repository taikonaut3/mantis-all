package io.github.astro.mantis.spring.cloud.configuration;

import io.github.astro.mantis.common.constant.Key;
import io.github.astro.mantis.common.util.StringUtils;
import io.github.astro.mantis.configuration.CallData;
import io.github.astro.mantis.configuration.ConsumerCaller;
import io.github.astro.mantis.configuration.RemoteUrl;
import io.github.astro.mantis.configuration.URL;
import io.github.astro.mantis.configuration.spi.ServiceProvider;
import io.github.astro.mantis.configuration.util.GenerateUtil;
import io.github.astro.mantis.governance.directory.Directory;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.context.ApplicationContext;

import java.util.List;
import java.util.Objects;

import static io.github.astro.mantis.common.constant.KeyValues.SPRING;

@ServiceProvider(SPRING)
public class SpringDiscoveryDirectory implements Directory {

    private ApplicationContext applicationContext;

    private DiscoveryClient discoveryClient;

    public void setApplicationContext(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
        discoveryClient = applicationContext.getBean(DiscoveryClient.class);
    }

    @Override
    public List<URL> list(CallData callData, URL... urls) {
        List<ServiceInstance> instances = discoveryClient.getInstances(callData.getApplicationName());
        ConsumerCaller caller = (ConsumerCaller) callData.getCaller();
        return instances.stream().map(serviceInstance -> {
            String protocolUrlStr = serviceInstance.getMetadata().get(GenerateUtil.generateMetaProtocolKey(caller.getProtocol().name()));
            String wight = serviceInstance.getMetadata().get(Key.REGISTRY_META_WEIGHT);
            if (StringUtils.isBlank(protocolUrlStr)) {
                return null;
            }
            URL protocolUrl = URL.valueOf(protocolUrlStr);
            RemoteUrl url = new RemoteUrl(caller.getProtocol().name(), protocolUrl.getAddress());
            url.setApplicationName(caller.getApplicationName());
            url.setRemoteServiceName(caller.getRemoteServiceName());
            url.setCallName(caller.getCallName());
            url.addParameter(Key.WEIGHT, wight);
            url.addParameters(protocolUrl.getParameters());
            return (URL) url;
        }).filter(Objects::nonNull).toList();
    }

    @Override
    public void destroy() {

    }

}
