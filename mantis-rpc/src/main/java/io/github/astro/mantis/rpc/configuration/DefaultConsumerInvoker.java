package io.github.astro.mantis.rpc.configuration;

import io.github.astro.mantis.common.util.AssertUtils;
import io.github.astro.mantis.common.util.StringUtils;
import io.github.astro.mantis.configuration.ConsumerInvoker;
import io.github.astro.mantis.configuration.MethodInvoker;
import io.github.astro.mantis.configuration.RemoteCaller;
import io.github.astro.mantis.configuration.annotation.Invoke;
import io.github.astro.mantis.configuration.base.AbstractCallOptions;
import io.github.astro.mantis.configuration.extension.MantisContext;
import io.github.astro.mantis.configuration.invoke.DefaultInvocation;
import io.github.astro.mantis.configuration.invoke.Invocation;
import io.github.astro.mantis.configuration.util.GenerateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

public class DefaultConsumerInvoker extends AbstractCallOptions implements ConsumerInvoker {

    private static final Logger logger = LoggerFactory.getLogger(DefaultConsumerInvoker.class);
    private static final Class<Invoke> INVOKECLASS = Invoke.class;

    private final RemoteCaller<?> remoteCaller;
    private final Method method;
    private MantisInvoker mantisInvoker;
    private String exportName;
    private String methodKey;
    private String directUrl;

    public DefaultConsumerInvoker(Method method, RemoteCaller<?> remoteCaller) {
        AssertUtils.assertCondition(method != null && remoteCaller != null);
        AssertUtils.assertCondition(checkInvoke(method), "Only support @Invoke modify Method");
        this.remoteCaller = remoteCaller;
        this.method = method;
        // parse @Invoke
        parse();
    }

    private static boolean checkInvoke(Method method) {
        return method.isAnnotationPresent(INVOKECLASS);
    }

    @Override
    public String getApplicationName() {
        return remoteCaller.getApplicationName();
    }

    @Override
    public String getExportName() {
        return exportName;
    }

    @Override
    public void setExportName(String exportName) {
        this.exportName = exportName;
    }

    @Override
    public Object invoke(Invocation invocation) {
        try {
            MantisContext.setCurrentBootStrap(getCaller().getMantisBootStrap());
            return doInvoke(invocation);
        } catch (Exception e) {
            logger.error(this + " Invoke Error", e);
            throw new RuntimeException(e);
        } finally {
            MantisContext.getContext().clear();
            // todo RpcContext
        }
    }

    private Object doInvoke(Invocation invocation) {
        ((DefaultInvocation) invocation).setReturnType(getReturnType());
        return mantisInvoker.invoke(invocation).getValue();
    }

    @Override
    protected void parseAnnotation() {
        Invoke invoke = method.getAnnotation(INVOKECLASS);
        this.setExportName(invoke.value());
        String methodKey = invoke.methodKey();
        if (StringUtils.isBlank(methodKey)) {
            methodKey = GenerateUtil.generateKey(getMethod());
        }
        this.setMethodKey(methodKey);
        this.setDirectUrl(invoke.url());
        this.setGroup(invoke.group()).setVersion(invoke.version())
                .setProxy(invoke.proxy()).setSerialize(invoke.serialize());
        this.setProtocol(invoke.protocol()).setFaultTolerance(invoke.faultTolerance())
                .setLoadBalance(invoke.loadBalance()).setTransport(invoke.transport()).setRouter(invoke.router())
                .setRetires(invoke.retires()).setAsync(invoke.async()).setTimeout(invoke.timeout());
        parseConfig(remoteCaller.getMantisBootStrap().getConfigurationManager(), invoke.registries(), invoke.processors());
    }

    @Override
    protected void parseAnnotationAfter() {
        this.mantisInvoker = new MantisInvoker(this);
    }

    @Override
    public Method getMethod() {
        return method;
    }

    @Override
    public String getMethodKey() {
        return methodKey;
    }

    public void setMethodKey(String methodKey) {
        this.methodKey = methodKey;
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

    public Map<String, String> getParams() {
        return parameterization();
    }

    @Override
    public MethodInvoker getMethodInvoker() {
        return remoteCaller;
    }

    @Override
    public RemoteCaller<?> getCaller() {
        return remoteCaller;
    }

    @Override
    public String getDirectUrl() {
        return directUrl;
    }

    public void setDirectUrl(String directUrl) {
        this.directUrl = directUrl;
    }

    @Override
    public String toString() {
        return exportName + "/" + methodKey + ":" + method.toString();
    }

}
