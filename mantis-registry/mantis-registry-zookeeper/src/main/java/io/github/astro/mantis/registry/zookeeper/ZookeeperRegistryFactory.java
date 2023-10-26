package io.github.astro.mantis.registry.zookeeper;

import io.github.astro.mantis.configuration.URL;
import io.github.astro.mantis.configuration.extension.spi.ServiceProvider;
import io.github.astro.mantis.registry.AbstractRegistryFactory;
import io.github.astro.mantis.registry.Registry;

import static io.github.astro.mantis.common.constant.ServiceType.RegistryFactory.ZOOKEEPER;

@ServiceProvider(ZOOKEEPER)
public class ZookeeperRegistryFactory extends AbstractRegistryFactory {

    @Override
    protected Registry create(URL url) {
        return new ZookeeperRegistry(url);
    }
}
