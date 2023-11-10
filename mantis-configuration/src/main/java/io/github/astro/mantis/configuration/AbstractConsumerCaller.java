package io.github.astro.mantis.configuration;

import io.github.astro.mantis.common.constant.Key;
import io.github.astro.mantis.common.constant.Mode;
import io.github.astro.mantis.common.constant.ModeContainer;
import io.github.astro.mantis.configuration.annotation.Call;
import io.github.astro.mantis.configuration.annotation.Parameter;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.concurrent.CompletableFuture;

@Accessors(chain = true)
@Setter
@Getter
public abstract class AbstractConsumerCaller extends AbstractCaller implements CallOptions {

    @Parameter(Key.RETRIES)
    protected int retires;

    @Parameter(Key.IS_ASYNC)
    private boolean async;

    @Parameter(Key.PROTOCOL)
    private String protocol;

    @Parameter(Key.LOAD_BALANCE)
    private String loadBalance;

    @Parameter(Key.DIRECTORY)
    private String directory;

    @Parameter(Key.ROUTER)
    private String router;

    @Parameter(Key.FAULT_TOLERANCE)
    private String faultTolerance;

    @Parameter(Key.TIMEOUT)
    private int timeout;

    @Parameter(Key.IS_ONEWAY)
    private boolean isOneWay;

    public AbstractConsumerCaller(Method method, CallerContainer callerContainer) {
        super.method = method;
        super.callerContainer = callerContainer;
        Call call = method.getAnnotation(Call.class);
        checkAsyncReturnType(call, method);
        setAsync(call.async());
        setRouter(call.router());
        setTimeout(call.timeout());
        setRetires(call.retires());
        setDirectory(call.directory());
        setLoadBalance(call.loadBalance());
        setProtocol(call.protocol());
        setFaultTolerance(call.faultTolerance());
        setOneWay(getReturnType().getTypeName().equals("void"));
        init();
    }

    @Override
    public Mode getProtocol() {
        return ModeContainer.getMode(Key.PROTOCOL, protocol);
    }

    private void checkAsyncReturnType(Call call, Method method) {
        Type returnType = method.getGenericReturnType();
        if (call.async()) {
            if (returnType instanceof ParameterizedType parameterizedType) {
                if (parameterizedType.getRawType() != CompletableFuture.class) {
                    throw new IllegalArgumentException("Async returnType should be CompletableFuture");
                }
            } else {
                throw new IllegalArgumentException("Async returnType should be CompletableFuture");
            }
        }
    }

}
