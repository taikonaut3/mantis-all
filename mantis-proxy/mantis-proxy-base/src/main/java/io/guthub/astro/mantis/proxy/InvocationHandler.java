package io.guthub.astro.mantis.proxy;

import java.lang.reflect.Method;

public interface InvocationHandler {

    default Object invoke(Object proxy, Method method, Object[] args, SuperInvoker<?> superInvoker) throws Throwable {
        return superInvoker.invoke();
    }

}
