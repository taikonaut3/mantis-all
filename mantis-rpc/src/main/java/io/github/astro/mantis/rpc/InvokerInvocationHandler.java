package io.github.astro.mantis.rpc;

import io.github.astro.mantis.configuration.Caller;
import io.github.astro.mantis.configuration.DefaultCallData;
import io.github.astro.mantis.configuration.RemoteCaller;
import io.github.astro.mantis.configuration.annotation.Call;
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
        if (method.isAnnotationPresent(Call.class)) {
            Caller caller = remoteCaller.getCaller(method);
            if (caller != null) {
                DefaultCallData data = new DefaultCallData(caller, args);
                return caller.call(data);
            }
        }
        return null;
    }

}
