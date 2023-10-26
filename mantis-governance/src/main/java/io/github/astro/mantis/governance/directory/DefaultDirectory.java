package io.github.astro.mantis.governance.directory;

import io.github.astro.mantis.common.util.CollectionUtils;
import io.github.astro.mantis.configuration.URL;
import io.github.astro.mantis.configuration.extension.spi.ServiceProvider;
import io.github.astro.mantis.configuration.extension.spi.ServiceProviderLoader;
import io.github.astro.mantis.configuration.invoke.Invocation;
import io.github.astro.mantis.registry.Registry;
import io.github.astro.mantis.registry.RegistryFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static io.github.astro.mantis.common.constant.ServiceType.Directory.DEFAULT;

/**
 * must open mantis-registry
 */
@ServiceProvider(DEFAULT)
public class DefaultDirectory implements Directory {

    private static final Logger logger = LoggerFactory.getLogger(DefaultDirectory.class);

    @Override
    public List<URL> list(Invocation invocation, URL... urls) {
        logger.debug("director......");
        ArrayList<URL> exporterUrls = new ArrayList<>();
        for (URL url : urls) {
            RegistryFactory registryFactory = ServiceProviderLoader.loadService(RegistryFactory.class, url.getProtocol());
            Registry registry = registryFactory.getRegistry(url);
            CollectionUtils.addToList(exporterUrls, (existUrl, newUrl) -> Objects.equals(existUrl.getAddress(), newUrl.getAddress()), registry.discover(invocation));
        }
        return exporterUrls;
    }

    @Override
    public void destroy() {

    }
}
