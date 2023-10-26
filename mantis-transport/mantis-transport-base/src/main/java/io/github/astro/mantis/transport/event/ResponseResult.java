package io.github.astro.mantis.transport.event;

import io.github.astro.mantis.common.exception.RpcException;
import io.github.astro.mantis.configuration.invoke.Result;

public class ResponseResult implements Result {

    private final ResponseFuture future;
    private final boolean isAsync;
    private boolean hasException = false;
    private Exception exception;

    public ResponseResult(ResponseFuture future, boolean isAsync) {
        this.future = future;
        this.isAsync = isAsync;
    }

    @Override
    public Object getValue() {
        try {
            if (isAsync) {
                return future;
            } else {
                return future.get();
            }
        } catch (Exception e) {
            this.hasException = true;
            this.exception = e;
            throw new RpcException(e);
        }
    }

    @Override
    public boolean hasResult() {
        return future.isDone();
    }

    @Override
    public boolean hasException() {
        return hasException;
    }

    @Override
    public Exception getException() {
        return exception;
    }

    public ResponseFuture getFuture() {
        return future;
    }
}
