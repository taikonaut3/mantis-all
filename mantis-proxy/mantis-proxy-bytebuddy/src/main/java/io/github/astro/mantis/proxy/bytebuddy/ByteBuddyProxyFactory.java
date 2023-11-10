package io.github.astro.mantis.proxy.bytebuddy;

import io.github.astro.mantis.configuration.spi.ServiceProvider;
import io.guthub.astro.mantis.proxy.AbstractProxyFactory;
import io.guthub.astro.mantis.proxy.InvocationHandler;
import net.bytebuddy.ByteBuddy;
import net.bytebuddy.implementation.MethodDelegation;
import net.bytebuddy.implementation.bind.annotation.*;
import net.bytebuddy.matcher.ElementMatchers;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.concurrent.Callable;

import static io.github.astro.mantis.common.constant.KeyValues.ProxyFactory.BYTEBUDDY;

/**
 * Create Proxy  performance is not good
 */
@SuppressWarnings("unchecked")
@ServiceProvider(BYTEBUDDY)
public class ByteBuddyProxyFactory extends AbstractProxyFactory {

    @Override
    protected <T> T doCreateProxy(Class<T> interfaceClass, InvocationHandler handler) {
        T dynamicProxy;
        try {
            dynamicProxy = new ByteBuddy()
                    .subclass(interfaceClass)
                    .method(ElementMatchers.isDeclaredBy(interfaceClass))
                    .intercept(MethodDelegation.to(new InterfaceInterceptor(handler)))
                    .make()
                    .load(ClassLoader.getSystemClassLoader())
                    .getLoaded().getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return dynamicProxy;
    }

    @Override
    protected <T> T doCreateProxy(T target, InvocationHandler handler) {
        Object dynamicProxy;
        try {
            dynamicProxy = new ByteBuddy()
                    .subclass(target.getClass())
                    .method(ElementMatchers.any())
                    .intercept(MethodDelegation.to(new InstanceInterceptor(handler)))
                    .make()
                    .load(ClassLoader.getSystemClassLoader())
                    .getLoaded().getConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException |
                 NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
        return (T) dynamicProxy;
    }

    public static class InterfaceInterceptor {

        private final InvocationHandler invocationHandler;

        public InterfaceInterceptor(InvocationHandler invocationHandler) {
            this.invocationHandler = invocationHandler;
        }

        @RuntimeType
        public Object interceptor(@This Object instance, @Origin Method method, @AllArguments Object[] args) throws Exception {
            try {
                return invocationHandler.invoke(instance, method, args, () -> null);
            } catch (Throwable e) {
                throw new RuntimeException(e);
            }

        }

    }

    public static class InstanceInterceptor {

        private final InvocationHandler invocationHandler;

        public InstanceInterceptor(InvocationHandler invocationHandler) {
            this.invocationHandler = invocationHandler;
        }

        @RuntimeType
        public Object intercept(@This Object proxy, @Origin Method method, @AllArguments Object[] args, @SuperCall Callable<?> callable) {
            try {
                return invocationHandler.invoke(proxy, method, args, callable::call);
            } catch (Throwable e) {
                throw new RuntimeException(e);
            }
        }

    }

}
