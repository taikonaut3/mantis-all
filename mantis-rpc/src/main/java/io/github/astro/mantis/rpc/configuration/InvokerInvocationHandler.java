package io.github.astro.mantis.rpc.configuration;

import io.github.astro.mantis.configuration.Invoker;
import io.github.astro.mantis.configuration.RemoteCaller;
import io.github.astro.mantis.configuration.annotation.Invoke;
import io.github.astro.mantis.configuration.invoke.DefaultInvocation;
import io.guthub.astro.mantis.proxy.InvocationHandler;
import io.guthub.astro.mantis.proxy.SuperInvoker;

import java.lang.reflect.Method;

public class InvokerInvocationHandler implements InvocationHandler {

    private final RemoteCaller<?> remoteCaller;

    public InvokerInvocationHandler(RemoteCaller<?> remoteCaller) {
        this.remoteCaller = remoteCaller;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args, SuperInvoker<?> superInvoker) throws Throwable {
        if (method.isAnnotationPresent(Invoke.class)) {
            Invoker invoker = remoteCaller.getInvoker(method);
            DefaultInvocation invocation = new DefaultInvocation(invoker, args);
            return invoker.invoke(invocation);
        }
        return null;
    }
}
