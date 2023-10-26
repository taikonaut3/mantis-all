package io.github.astro.mantis.spring.rpc;

import io.github.astro.mantis.configuration.Invoker;
import io.github.astro.mantis.configuration.MantisBootStrap;
import io.github.astro.mantis.rpc.configuration.DefaultExporter;

import java.lang.reflect.Method;

public class SpringExporter<T> extends DefaultExporter<T> {

    public SpringExporter(MantisBootStrap mantisBootStrap, T target) {
        super(mantisBootStrap, target);
    }

    @Override
    public Invoker createInvoker(Method method) {
        return new SpringProviderInvoker(method, this);
    }

}
