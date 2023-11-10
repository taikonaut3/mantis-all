package io.github.astro.mantis.spring.rpc;

import io.github.astro.mantis.configuration.Caller;
import io.github.astro.mantis.configuration.MantisApplication;
import io.github.astro.mantis.rpc.DefaultRemoteCaller;

import java.lang.reflect.Method;

public class SpringRemoteCaller<T> extends DefaultRemoteCaller<T> {

    public SpringRemoteCaller(MantisApplication mantisApplication, Class<T> interfaceType) {
        super(mantisApplication, interfaceType);
    }

    @Override
    public Caller createCaller(Method method) {
        return new SpringConsumerCaller(method, this);
    }

}
