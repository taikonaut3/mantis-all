package io.github.astro.mantis.common.exception;

import java.util.concurrent.TimeoutException;

/**
 * mantis 调用超时
 */
public class RpcTimeoutException extends TimeoutException {

    private static final String msg = "mantis 调用超时,默认超时时间为：";

    public RpcTimeoutException(int timeout) {
        super(msg + timeout);
    }
}
