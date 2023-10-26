package io.github.astro.mantis.common.exception;

public class RpcException extends RuntimeException {

    public RpcException(String msg, Throwable e) {
        super(msg, e);
    }

    public RpcException(String msg) {
        super(msg);
    }

    public RpcException(Throwable msg) {
        super(msg);
    }
}
