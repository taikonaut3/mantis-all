package io.github.astro.mantis.configuration;

import java.lang.reflect.Method;

public interface Exporter<T> extends MethodInvoker, Source {

    void export();

    T getTarget();

    void setTarget(T target);

    Method getMethod(String methodKey);

    Invoker getInvoker(String methodKey);
}
