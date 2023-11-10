package io.github.astro.mantis.configuration;

import io.github.astro.mantis.common.constant.Mode;

public interface CallOptions extends Options {

    boolean isAsync();

    String getLoadBalance();

    String getDirectory();

    String getRouter();

    String getFaultTolerance();

    String getTransport();

    int getTimeout();

    int getRetires();

    Mode getProtocol();

}
