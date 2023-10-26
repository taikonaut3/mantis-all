package io.github.astro.mantis.configuration;

import io.github.astro.mantis.configuration.base.CallOptions;

public interface ConsumerInvoker extends Invoker, CallOptions {

    RemoteCaller<?> getCaller();

    String getDirectUrl();
}
