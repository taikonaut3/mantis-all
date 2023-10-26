package io.guthub.astro.mantis.proxy;

import io.github.astro.mantis.configuration.extension.spi.ServiceInterface;

import static io.github.astro.mantis.common.constant.ServiceType.ProxyFactory.JDK;

@ServiceInterface(JDK)
public interface ProxyFactory {

    <T> T createProxy(Class<T> interfaceClass, InvocationHandler handler);

    <T> T createProxy(T target, InvocationHandler handler);

}
