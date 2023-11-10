package io.github.astro.mantis.configuration;

import java.lang.reflect.Method;

public interface RemoteService<T> extends CallerContainer, Source {

    T getTarget();

    Method getMethod(String callName);

    Caller getCaller(String callName);

}
