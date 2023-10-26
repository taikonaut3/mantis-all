package io.github.astro.mantis.common.exception;

public class ConversionException extends RuntimeException {

    public ConversionException(String msg, Throwable e) {
        super(msg, e);
    }

    public ConversionException(String msg) {
        super(msg);
    }

    public ConversionException(Throwable msg) {
        super(msg);
    }
}
