package io.github.astro.mantis.registry;

import io.github.astro.mantis.configuration.URL;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

public class DefaultRegistryListener implements RegistryListener {

    private static final Logger logger = LoggerFactory.getLogger(DefaultRegistryListener.class);

    private final Map<String, URL> discoverServices;

    public DefaultRegistryListener(Map<String, URL> discoverServices) {
        this.discoverServices = discoverServices;
    }

    @Override
    public void listenDelete(URL url) {
        discoverServices.remove(url.pathsToString());
    }

    @Override
    public void listenChanged(URL oldUrl, URL newUrl) {
        discoverServices.put(oldUrl.pathsToString(), newUrl);
    }

    @Override
    public void listenCreated(URL url) {

    }

}
