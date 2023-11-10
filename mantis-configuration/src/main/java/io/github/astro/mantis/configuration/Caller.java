package io.github.astro.mantis.configuration;

import java.lang.reflect.Method;
import java.lang.reflect.Type;

public interface Caller extends Options, Source, Lifecycle {

    Object call(CallData data);

    Method getMethod();

    String getCallName();

    Type getReturnType();

    CallerContainer getCallerContainer();

}
