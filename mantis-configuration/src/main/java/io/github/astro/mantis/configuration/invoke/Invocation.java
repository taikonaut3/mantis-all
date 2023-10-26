package io.github.astro.mantis.configuration.invoke;

import io.github.astro.mantis.configuration.Invoker;
import io.github.astro.mantis.configuration.Source;

import java.lang.reflect.Type;

public interface Invocation extends Source {

    String getMethodKey();

    Object[] getArgs();

    Type getReturnType();

    Type[] getParameterTypes();

    Invoker getInvoker();

}
