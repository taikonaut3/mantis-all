package io.github.astro.mantis.configuration;

public interface ConsumerCaller extends Caller, CallOptions {

    RemoteCaller<?> getRemoteCaller();

    String getDirectUrl();

}
