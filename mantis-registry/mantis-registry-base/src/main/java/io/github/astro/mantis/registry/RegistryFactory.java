package io.github.astro.mantis.registry;

import io.github.astro.mantis.configuration.URL;
import io.github.astro.mantis.configuration.spi.ServiceInterface;

import static io.github.astro.mantis.common.constant.KeyValues.RegistryFactory.ZOOKEEPER;

@ServiceInterface(ZOOKEEPER)
public interface RegistryFactory {

    /**
     * 获取注册中心实例
     */
    Registry getRegistry(URL url);

}

