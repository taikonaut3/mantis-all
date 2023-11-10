package io.github.astro.mantis.registry;

import io.github.astro.mantis.configuration.CallData;
import io.github.astro.mantis.configuration.RemoteUrl;
import io.github.astro.mantis.configuration.URL;
import io.github.astro.mantis.configuration.util.GenerateUtil;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public abstract class AbstractRegistry implements Registry {

    private final Map<String, URL> registeredServices = new ConcurrentHashMap<>();

    private final Map<String, URL> discoverServices = new ConcurrentHashMap<>();

    protected RegistryListener listener;

    protected AbstractRegistry(URL url) {
        listener = new DefaultRegistryListener(discoverServices);
        connect(url);
    }

    @Override
    public void register(RemoteUrl url) {
        String registerKey = GenerateUtil.generateKey(url);
        URL registeredUrl = registeredServices.get(registerKey);
        if (registeredUrl == null) {
            doRegister(url);
            registeredServices.put(registerKey, url);
        }
    }

    @Override
    public URL discover(CallData callData) {
        String discoverKey = GenerateUtil.generateKey(callData);
        URL url = discoverServices.get(discoverKey);
        if (url == null) {
            url = doDiscover(callData);
            if (url != null) {
                discoverServices.put(discoverKey, url);
                subscribe(url);
            }
        }
        return url;
    }

    @Override
    public void connect(URL url) {
        initClient(url);
        doConnect(url);
    }

    protected abstract void initClient(URL url);

    protected abstract void doConnect(URL configuration);

    protected abstract void doRegister(RemoteUrl url);

    protected abstract URL doDiscover(CallData callData);

}
