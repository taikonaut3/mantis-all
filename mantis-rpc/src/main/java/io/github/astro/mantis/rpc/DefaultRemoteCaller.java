package io.github.astro.mantis.rpc;

import io.github.astro.mantis.common.util.AssertUtils;
import io.github.astro.mantis.configuration.AbstractCallerContainer;
import io.github.astro.mantis.configuration.Caller;
import io.github.astro.mantis.configuration.MantisApplication;
import io.github.astro.mantis.configuration.RemoteCaller;
import io.github.astro.mantis.configuration.annotation.Call;
import io.github.astro.mantis.configuration.spi.ExtensionLoader;
import io.guthub.astro.mantis.proxy.ProxyFactory;

import java.lang.reflect.Method;

public class DefaultRemoteCaller<T> extends AbstractCallerContainer implements RemoteCaller<T> {

    private static final Class<io.github.astro.mantis.configuration.annotation.RemoteCaller> REMOTE_CALL_CLASS =
            io.github.astro.mantis.configuration.annotation.RemoteCaller.class;

    private final Class<T> baseOnClass;

    private String remoteApplicationName;

    private volatile T proxy;

    public DefaultRemoteCaller(MantisApplication mantisApplication, Class<T> interfaceType) {
        super(mantisApplication);
        baseOnClass = interfaceType;
        init();
    }

    private static boolean checkRemoteCall(Class<?> interfaceType) {
        return interfaceType.isInterface() && interfaceType.isAnnotationPresent(REMOTE_CALL_CLASS);
    }

    @Override
    protected void initBefore() {
        AssertUtils.assertNotNull(baseOnClass);
        AssertUtils.assertCondition(checkRemoteCall(baseOnClass), "RemoteCaller's this Method only support @RemoteCall modifier's Interface");
    }

    @Override
    protected void doInit() {
        // parse @RemoteCaller
        parseRemoteCaller();
        // parse @Call
        parseCall();
    }

    @Override
    protected void initAfter() {
        mantisApplication.addRemoteCaller(this);
        ProxyFactory proxyFactory = ExtensionLoader.loadService(ProxyFactory.class, getProxyMode());
        proxy = proxyFactory.createProxy(baseOnClass, new RpcInvocationHandler(this));
    }

    @Override
    public void start() {
        for (Caller caller : getCallers()) {
            caller.start();
        }
    }

    @Override
    public T get() {
        return proxy;
    }

    @Override
    public String getRemoteApplicationName() {
        return remoteApplicationName;
    }

    @Override
    public Class<T> getInterface() {
        return baseOnClass;
    }

    @Override
    public Caller createCaller(Method method) {
        return new DefaultConsumerCaller(method, this);
    }

    private void parseRemoteCaller() {
        io.github.astro.mantis.configuration.annotation.RemoteCaller call = baseOnClass.getAnnotation(REMOTE_CALL_CLASS);
        remoteApplicationName = call.value();
        proxyMode = call.proxy();
    }

    private void parseCall() {
        for (Method method : baseOnClass.getDeclaredMethods()) {
            if (method.isAnnotationPresent(Call.class)) {
                Caller caller = createCaller(method);
                callerMap.put(method, caller);
            }
        }
    }

    @Override
    public String toString() {
        return remoteApplicationName + " [" + baseOnClass.getName() + "]";
    }

}
