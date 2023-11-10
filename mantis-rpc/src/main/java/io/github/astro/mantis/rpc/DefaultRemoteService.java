package io.github.astro.mantis.rpc;

import io.github.astro.mantis.common.util.AssertUtils;
import io.github.astro.mantis.configuration.AbstractCallerContainer;
import io.github.astro.mantis.configuration.Caller;
import io.github.astro.mantis.configuration.MantisApplication;
import io.github.astro.mantis.configuration.RemoteService;
import io.github.astro.mantis.configuration.annotation.Callable;

import java.lang.reflect.Method;

public class DefaultRemoteService<T> extends AbstractCallerContainer implements RemoteService<T> {

    private static final Class<io.github.astro.mantis.configuration.annotation.RemoteService> REMOTE_SERVICE_CLASS =
            io.github.astro.mantis.configuration.annotation.RemoteService.class;

    private final Class<T> remoteServiceClass;

    private final T target;

    private String remoteServiceName;

    @SuppressWarnings("unchecked")
    public DefaultRemoteService(MantisApplication mantisApplication, T target) {
        super(mantisApplication);
        this.target = target;
        this.remoteServiceClass = (Class<T>) target.getClass();
        init();

    }

    public static boolean checkExport(Class<?> remoteServiceClass) {
        return remoteServiceClass.isAnnotationPresent(REMOTE_SERVICE_CLASS);
    }

    @Override
    protected void initBefore() {
        AssertUtils.assertNotNull(target);
        AssertUtils.assertCondition(checkExport(target.getClass()), "remoteService this Method Only support @Export modifier's Object");
    }

    @Override
    protected void doInit() {
        // parse @RemoteService
        parseRemoteService();
        // parse @Callable
        parseCallable();
    }

    @Override
    protected void initAfter() {
        mantisApplication.addRemoteService(this);
    }

    @Override
    public void start() {
        for (Caller caller : getCallers()) {
            caller.start();
        }
    }

    private void parseRemoteService() {
        io.github.astro.mantis.configuration.annotation.RemoteService remoteService = remoteServiceClass.getAnnotation(REMOTE_SERVICE_CLASS);
        remoteServiceName = remoteService.value();
        proxyMode = remoteService.proxy();

    }

    private void parseCallable() {
        for (Method method : remoteServiceClass.getDeclaredMethods()) {
            if (method.isAnnotationPresent(Callable.class)) {
                Caller caller = createCaller(method);
                callerMap.put(method, caller);
            }
        }
    }

    @Override
    public Caller createCaller(Method method) {
        return new DefaultProviderCaller(method, this);
    }

    @Override
    public T getTarget() {
        return target;
    }

    @Override
    public Method getMethod(String callName) {
        Caller caller = getCaller(callName);
        return caller == null ? null : caller.getMethod();
    }

    @Override
    public Caller getCaller(String callName) {
        for (Caller caller : callerMap.values()) {
            if (caller.getCallName().equals(callName)) {
                return caller;
            }
        }
        return null;
    }

    @Override
    public String getApplicationName() {
        return mantisApplication.getApplicationName();
    }

    @Override
    public String getRemoteServiceName() {
        return remoteServiceName;
    }

    @Override
    public String toString() {
        return getApplicationName() + "." + remoteServiceName + "[" + remoteServiceClass.getName() + "]";
    }

}
