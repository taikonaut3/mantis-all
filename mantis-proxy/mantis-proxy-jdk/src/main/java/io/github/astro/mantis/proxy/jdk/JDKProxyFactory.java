package io.github.astro.mantis.proxy.jdk;

import io.github.astro.mantis.configuration.extension.spi.ServiceProvider;
import io.guthub.astro.mantis.proxy.AbstractProxyFactory;
import io.guthub.astro.mantis.proxy.InvocationHandler;

import java.lang.reflect.Proxy;

import static io.github.astro.mantis.common.constant.ServiceType.ProxyFactory.JDK;

@ServiceProvider(JDK)
public class JDKProxyFactory extends AbstractProxyFactory {

    @Override
    @SuppressWarnings("unchecked")
    protected <T> T doCreateProxy(Class<T> interfaceClass, InvocationHandler handler) {
        return (T) Proxy.newProxyInstance(ClassLoader.getSystemClassLoader(), new Class[]{interfaceClass}, (proxy, method, args) -> handler.invoke(proxy, method, args, () -> null));
    }

    @Override
    @SuppressWarnings("unchecked")
    protected <T> T doCreateProxy(T target, InvocationHandler handler) {
        return (T) Proxy.newProxyInstance(ClassLoader.getSystemClassLoader(), target.getClass().getInterfaces(), (proxy, method, args) -> handler.invoke(proxy, method, args, () -> method.invoke(target, args)));
    }
}
