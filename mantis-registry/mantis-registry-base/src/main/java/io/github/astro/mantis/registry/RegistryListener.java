package io.github.astro.mantis.registry;

import io.github.astro.mantis.configuration.URL;

public interface RegistryListener {

    /**
     * 监听这个暴露再注册中心中接口的变化
     *
     * @param url
     */
    void listenDelete(URL url);

    void listenChanged(URL oldUrl, URL newUrl);

    void listenCreated(URL url);
}
