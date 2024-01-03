package io.github.astro.mantis.transport;

import io.github.astro.mantis.common.exception.RpcException;
import io.github.astro.mantis.configuration.Result;

import java.util.concurrent.ExecutionException;

public class ResponseResult implements Result {

    private final ResponseFuture future;

    private final boolean isAsync;

    private boolean hasException = false;

    private RpcException exception;

    public ResponseResult(ResponseFuture future, boolean isAsync) {
        this.future = future;
        this.isAsync = isAsync;
        if (!isAsync) {
            getValue();
        }
    }

    @Override
    public Object getValue() {
        if (isAsync) {
            return future;
        } else {
            try {
                return future.get();
            } catch (InterruptedException e) {
                hasException = true;
                exception = new RpcException(e);
                throw exception;
            } catch (ExecutionException e) {
                hasException = true;
                exception = (RpcException) e.getCause();
                throw exception;
            }
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
