package io.github.astro.mantis.rpc;

import io.github.astro.mantis.common.exception.RpcException;
import io.github.astro.mantis.common.util.AssertUtils;
import io.github.astro.mantis.common.util.StringUtils;
import io.github.astro.mantis.configuration.AbstractConsumerCaller;
import io.github.astro.mantis.configuration.CallData;
import io.github.astro.mantis.configuration.ConsumerCaller;
import io.github.astro.mantis.configuration.RemoteCaller;
import io.github.astro.mantis.configuration.annotation.Call;
import io.github.astro.mantis.configuration.extension.MantisContext;
import io.github.astro.mantis.configuration.util.GenerateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.concurrent.CompletableFuture;

public class DefaultConsumerCaller extends AbstractConsumerCaller implements ConsumerCaller {

    private static final Logger logger = LoggerFactory.getLogger(DefaultConsumerCaller.class);

    private static final Class<Call> CALL_CLASS = Call.class;

    private MantisInvoker mantisInvoker;

    private String directUrl;

    public DefaultConsumerCaller(Method method, RemoteCaller<?> remoteCaller) {
        super(method, remoteCaller);
    }

    private static boolean checkInvoke(Method method) {
        return method.isAnnotationPresent(CALL_CLASS);
    }

    @Override
    protected void initBefore() {
        AssertUtils.assertCondition(method != null && getRemoteCaller() != null);
        AssertUtils.assertCondition(checkInvoke(method), "Only support @Call modify Method");
    }

    @Override
    protected void doInit() {
        Call call = method.getAnnotation(CALL_CLASS);
        applicationName = getRemoteCaller().getRemoteApplicationName();
        remoteServiceName = call.value();
        callName = StringUtils.isBlank(call.callName()) ? GenerateUtil.generateKey(getMethod()) : call.callName();
        directUrl = call.url();
        parseOption(call.option(), getRemoteCaller().getMantisApplication());
    }

    @Override
    protected void initAfter() {

    }

    @Override
    public void start() {
        mantisInvoker = new MantisInvoker(this);
    }

    @Override
    public Object call(CallData data) {
        Object result = null;
        try {
            result = mantisInvoker.invoke(data).getValue();
        } catch (RpcException e) {
            logger.error("Remote Call Exception " + this, e);
        } finally {
            MantisContext.getContext().clear();
        }
        return result;
    }

    @Override
    public Type getReturnType() {
        Type returnType = method.getGenericReturnType();
        if (isAsync()) {
            if (returnType instanceof ParameterizedType parameterizedType) {
                if (parameterizedType.getRawType() != CompletableFuture.class) {
                    throw new IllegalArgumentException("Async returnType should be CompletableFuture");
                }
                return parameterizedType.getActualTypeArguments()[0];
            }
            throw new IllegalArgumentException("Async returnType should be CompletableFuture");
        } else {
            return returnType;
        }
    }

    @Override
    public RemoteCaller<?> getRemoteCaller() {
        return (RemoteCaller<?>) getCallerContainer();
    }

    @Override
    public String getDirectUrl() {
        return directUrl;
    }

}
