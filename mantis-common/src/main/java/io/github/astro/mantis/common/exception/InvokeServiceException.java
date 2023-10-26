package io.github.astro.mantis.common.exception;

public class InvokeServiceException extends RpcException {

    public InvokeServiceException(String msg, Throwable e) {
        super(msg, e);
    }
}
