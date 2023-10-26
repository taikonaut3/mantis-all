package io.github.astro.mantis.configuration;

public interface RemoteCaller<T> extends MethodInvoker {

    Class<T> getInterface();

    T get();

    String getApplicationName();

    void setApplicationName(String applicationName);
}
