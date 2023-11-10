package io.github.astro.mantis.governance.directory;

import io.github.astro.mantis.common.util.CollectionUtils;
import io.github.astro.mantis.configuration.CallData;
import io.github.astro.mantis.configuration.URL;
import io.github.astro.mantis.configuration.spi.ExtensionLoader;
import io.github.astro.mantis.configuration.spi.ServiceProvider;
import io.github.astro.mantis.registry.Registry;
import io.github.astro.mantis.registry.RegistryFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static io.github.astro.mantis.common.constant.KeyValues.Directory.DEFAULT;

/**
 * must open mantis-registry
 */
@ServiceProvider(DEFAULT)
public class DefaultDirectory implements Directory {

    private static final Logger logger = LoggerFactory.getLogger(DefaultDirectory.class);

    @Override
    public List<URL> list(CallData callData, URL... urls) {
        logger.debug("director......");
        ArrayList<URL> remoteServiceUrls = new ArrayList<>();
        for (URL url : urls) {
            RegistryFactory registryFactory = ExtensionLoader.loadService(RegistryFactory.class, url.getProtocol());
            Registry registry = registryFactory.getRegistry(url);
            CollectionUtils.addToList(remoteServiceUrls, (existUrl, newUrl) -> Objects.equals(existUrl.getAddress(), newUrl.getAddress()), registry.discover(callData));
        }
        return remoteServiceUrls;
    }

    @Override
    public void destroy() {

    }

}
