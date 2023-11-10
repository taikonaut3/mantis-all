package io.github.astro.mantis.configuration;

import java.lang.reflect.Type;

public interface CallData extends Source {

    Object[] getArgs();

    String getCallName();

    Type getReturnType();

    Type[] getParameterTypes();

    Caller getCaller();

}
