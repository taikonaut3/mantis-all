package io.github.astro.mantis.configuration;

import io.github.astro.mantis.common.exception.RpcException;

@FunctionalInterface
public interface DirectRemoteCall {

    Result call(URL url, CallData callData) throws RpcException;

}
