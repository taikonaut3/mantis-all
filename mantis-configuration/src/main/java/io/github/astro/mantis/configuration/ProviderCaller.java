package io.github.astro.mantis.configuration;

public interface ProviderCaller extends Caller {

    RemoteService<?> getRemoteService();

    RemoteUrl[] getRemoteUrls();

}
