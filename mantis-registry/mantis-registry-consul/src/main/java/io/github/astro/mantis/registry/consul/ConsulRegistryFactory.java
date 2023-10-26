package io.github.astro.mantis.registry.consul;

import io.github.astro.mantis.configuration.URL;
import io.github.astro.mantis.configuration.extension.spi.ServiceProvider;
import io.github.astro.mantis.registry.AbstractRegistryFactory;
import io.github.astro.mantis.registry.Registry;

import static io.github.astro.mantis.common.constant.ServiceType.RegistryFactory.CONSUL;

/**
 * 官方并没有很好的java client，mantis-registry-consul占时不完善，不建议使用
 */
@ServiceProvider(CONSUL)
public class ConsulRegistryFactory extends AbstractRegistryFactory {

    @Override
    protected Registry create(URL url) {
        return new ConsulRegistry(url);
    }
}
