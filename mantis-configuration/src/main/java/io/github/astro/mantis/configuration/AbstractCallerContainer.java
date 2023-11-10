package io.github.astro.mantis.configuration;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public abstract class AbstractCallerContainer extends ParseAnnotationLifecycle implements CallerContainer {

    protected Map<Method, Caller> callerMap = new HashMap<>();

    protected MantisApplication mantisApplication;

    protected String proxyMode;

    protected AbstractCallerContainer(MantisApplication mantisApplication) {
        this.mantisApplication = mantisApplication;
    }

    @Override
    public Caller[] getCallers() {
        return callerMap.values().toArray(new Caller[0]);
    }

    @Override
    public Caller getCaller(Method method) {
        return callerMap.get(method);
    }

    @Override
    public MantisApplication getMantisApplication() {
        return mantisApplication;
    }

    @Override
    public String getProxyMode() {
        return proxyMode;
    }

}
