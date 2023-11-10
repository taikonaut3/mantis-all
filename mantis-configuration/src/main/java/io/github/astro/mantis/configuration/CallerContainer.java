package io.github.astro.mantis.configuration;

import java.lang.reflect.Method;

public interface CallerContainer extends Lifecycle {

    Caller createCaller(Method method);

    Caller[] getCallers();

    Caller getCaller(Method method);

    MantisApplication getMantisApplication();

    String getProxyMode();

}
