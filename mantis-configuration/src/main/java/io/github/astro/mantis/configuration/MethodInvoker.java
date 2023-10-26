package io.github.astro.mantis.configuration;

import io.github.astro.mantis.configuration.invoke.Invocation;

import java.lang.reflect.Method;

public interface MethodInvoker {

    Invoker createInvoker(Method method);

    Object invoke(Invocation invocation);

    void addInvoker(Invoker... invokers);

    Invoker[] getInvokers();

    Invoker getInvoker(Method method);

    MantisBootStrap getMantisBootStrap();
}
