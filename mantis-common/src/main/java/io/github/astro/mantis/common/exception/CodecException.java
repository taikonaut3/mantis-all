package io.github.astro.mantis.common.exception;

public class CodecException extends NetWorkException {

    public CodecException(String msg, Throwable e) {
        super(msg, e);
    }

    public CodecException(String msg) {
        super(msg);
    }

    public CodecException(Throwable msg) {
        super(msg);
    }
}
