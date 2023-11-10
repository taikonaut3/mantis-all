package io.github.astro.mantis.configuration;

public interface RemoteCaller<T> extends CallerContainer {

    Class<T> getInterface();

    T get();

    String getRemoteApplicationName();

}
