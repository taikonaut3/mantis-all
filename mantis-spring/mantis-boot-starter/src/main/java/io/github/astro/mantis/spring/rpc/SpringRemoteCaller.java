package io.github.astro.mantis.spring.rpc;

import io.github.astro.mantis.configuration.Invoker;
import io.github.astro.mantis.configuration.MantisBootStrap;
import io.github.astro.mantis.rpc.configuration.DefaultRemoteCaller;

import java.lang.reflect.Method;

public class SpringRemoteCaller<T> extends DefaultRemoteCaller<T> {

    public SpringRemoteCaller(MantisBootStrap mantisBootStrap, Class<T> interfaceType) {
        super(mantisBootStrap, interfaceType);
    }

    @Override
    public Invoker createInvoker(Method method) {
        return new SpringConsumerInvoker(method, this);
    }
}
