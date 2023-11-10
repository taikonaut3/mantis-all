package io.github.astro.mantis.spring.rpc;

import io.github.astro.mantis.configuration.Caller;
import io.github.astro.mantis.configuration.MantisApplication;
import io.github.astro.mantis.rpc.DefaultRemoteService;

import java.lang.reflect.Method;

public class SpringRemoteService<T> extends DefaultRemoteService<T> {

    public SpringRemoteService(MantisApplication mantisApplication, T target) {
        super(mantisApplication, target);
    }

    @Override
    public Caller createCaller(Method method) {
        return new SpringProviderCaller(method, this);
    }

}
