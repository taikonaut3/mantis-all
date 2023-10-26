package io.github.astro.mantis.registry;

import io.github.astro.mantis.configuration.ExporterURL;
import io.github.astro.mantis.configuration.URL;
import io.github.astro.mantis.configuration.invoke.Invocation;

public interface Registry {

    boolean isConnected();

    void connect(URL url);

    void register(ExporterURL url);

    URL discover(Invocation invocation);

    void subscribe(URL url);

    void unSubscribe(URL url);

    void destroy();

//    void register(String serviceName, URL url) throws RpcException;
//    void unregister(String serviceName, URL url) throws RpcException;
//    List<URL> discover(String serviceName) throws RpcException;
//    void subscribe(String serviceName, URL url, Callback callback);
//    void unsubscribe(String serviceName, URL url, Callback callback);
//    void destroy();
}
