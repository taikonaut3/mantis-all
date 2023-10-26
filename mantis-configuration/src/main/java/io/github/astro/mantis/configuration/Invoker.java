package io.github.astro.mantis.configuration;

import io.github.astro.mantis.configuration.base.Options;
import io.github.astro.mantis.configuration.invoke.Invocation;

import java.lang.reflect.Method;
import java.lang.reflect.Type;

public interface Invoker extends Source, Options {

    Object invoke(Invocation invocation);

    Method getMethod();

    String getMethodKey();

    Type getReturnType();

    MethodInvoker getMethodInvoker();

}
