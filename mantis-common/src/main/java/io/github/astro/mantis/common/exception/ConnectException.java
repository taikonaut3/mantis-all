package io.github.astro.mantis.common.exception;

public class ConnectException extends NetWorkException {

    public ConnectException(Throwable t) {
        super(t);
    }

    public ConnectException(String msg) {
        super(msg);
    }

    public ConnectException(String msg, Throwable e) {
        super(msg, e);
    }

}
