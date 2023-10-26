package io.github.astro.mantis.configuration.base;

import io.github.astro.mantis.common.constant.ProtocolType;

public interface CallOptions extends Options {

    boolean isAsync();

    String getLoadBalance();

    String getDirectory();

    String getRouter();

    String getFaultTolerance();

    String getTransport();

    int getTimeout();

    int getRetires();

    ProtocolType getProtocol();

}
