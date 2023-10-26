package io.github.astro.mantis.rpc.configuration;

import io.github.astro.mantis.common.util.AssertUtils;
import io.github.astro.mantis.common.util.ReflectUtils;
import io.github.astro.mantis.configuration.Invoker;
import io.github.astro.mantis.configuration.MantisBootStrap;
import io.github.astro.mantis.configuration.RemoteCaller;
import io.github.astro.mantis.configuration.annotation.Invoke;
import io.github.astro.mantis.configuration.annotation.RemoteCall;
import io.github.astro.mantis.configuration.extension.spi.ServiceProviderLoader;
import io.github.astro.mantis.configuration.invoke.Invocation;
import io.guthub.astro.mantis.proxy.ProxyFactory;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class DefaultRemoteCaller<T> implements RemoteCaller<T> {

    private static final Class<RemoteCall> REMOTE_CALL_CLASS = RemoteCall.class;
    private final MantisBootStrap mantisBootStrap;
    private String applicationName;
    private Class<T> baseOnClass;
    private volatile T proxy;
    private List<Invoker> invokers;

    public DefaultRemoteCaller(MantisBootStrap mantisBootStrap, Class<T> interfaceType) {
        this.mantisBootStrap = mantisBootStrap;
        this.baseOn(interfaceType);
        mantisBootStrap.addRemoteCaller(this);
    }

    private static boolean checkRemoteCall(Class<?> interfaceType) {
        return interfaceType.isInterface() && interfaceType.isAnnotationPresent(REMOTE_CALL_CLASS);
    }

    public void baseOn(Class<T> interfaceType) {
        AssertUtils.assertCondition(checkRemoteCall(interfaceType), "RemoteCaller's this Method only support @RemoteCall modifier's Interface");
        this.baseOnClass = interfaceType;
        // 解析 @remoteCall
        parseRemoteCall();
        // 构建 remoteCaller中的invoker
        buildInvoker();
    }

    @Override
    public T get() {
        if (baseOnClass == null) {
            return null;
        }
        if (proxy == null) {
            synchronized (this) {
                if (proxy == null) {
                    proxy = ServiceProviderLoader.loadService(ProxyFactory.class).createProxy(baseOnClass, new InvokerInvocationHandler(this));
                }
            }
        }
        return proxy;
    }

    @Override
    public void addInvoker(Invoker... invokers) {
        this.invokers.addAll(Arrays.asList(invokers));
    }

    @Override
    public Object invoke(Invocation invocation) {
        return invocation.getInvoker().invoke(invocation);
    }

    @Override
    public Class<T> getInterface() {
        return baseOnClass;
    }

    @Override
    public Invoker[] getInvokers() {
        return invokers.toArray(new Invoker[0]);
    }

    @Override
    public Invoker getInvoker(Method method) {
        for (Invoker invoker : invokers) {
            if (ReflectUtils.isSameMethod(method, invoker.getMethod())) {
                return invoker;
            }
        }
        return null;
    }

    @Override
    public MantisBootStrap getMantisBootStrap() {
        return mantisBootStrap;
    }

    @Override
    public String getApplicationName() {
        return applicationName;
    }

    @Override
    public void setApplicationName(String applicationName) {
        this.applicationName = applicationName;
    }

    private void parseRemoteCall() {
        RemoteCall call = baseOnClass.getAnnotation(REMOTE_CALL_CLASS);
        this.setApplicationName(call.value());
    }

    private void buildInvoker() {
        if (invokers == null) {
            invokers = new LinkedList<>();
        }
        for (Method method : baseOnClass.getDeclaredMethods()) {
            if (method.isAnnotationPresent(Invoke.class)) {
                Invoker invoker = createInvoker(method);
                addInvoker(invoker);
            }
        }
    }

    @Override
    public Invoker createInvoker(Method method) {
        return new DefaultConsumerInvoker(method, this);
    }

    @Override
    public String toString() {
        return applicationName + ":" + baseOnClass.getName();
    }
}
