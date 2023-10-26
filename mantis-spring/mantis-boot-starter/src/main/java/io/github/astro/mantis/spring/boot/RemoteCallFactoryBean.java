package io.github.astro.mantis.spring.boot;

import io.github.astro.mantis.configuration.MantisBootStrap;
import io.github.astro.mantis.configuration.RemoteCaller;
import io.github.astro.mantis.spring.rpc.SpringRemoteCaller;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.FactoryBean;

public class RemoteCallFactoryBean<T> implements FactoryBean<T> {
    private final Class<T> interfaceType;

    private volatile RemoteCaller<T> remoteCaller;

    public RemoteCallFactoryBean(Class<T> interfaceType) {
        this.interfaceType = interfaceType;
    }

    @Resource
    public void setMantisConfigurationProperties(MantisConfigurationProperties properties) {
        MantisBootStrap mantisBootStrap = properties.getMantisBootStrap();
        remoteCaller = new SpringRemoteCaller<>(mantisBootStrap, interfaceType);
    }

    @Override
    public T getObject() throws Exception {
        return remoteCaller.get();
    }

    @Override
    public Class<?> getObjectType() {
        return interfaceType;
    }
}
