package io.github.astro.mantis.proxy.cglib;

import io.github.astro.mantis.configuration.extension.spi.ServiceProvider;
import io.guthub.astro.mantis.proxy.AbstractProxyFactory;
import io.guthub.astro.mantis.proxy.InvocationHandler;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;

import static io.github.astro.mantis.common.constant.ServiceType.ProxyFactory.CGLIB;

@ServiceProvider(CGLIB)
public class CGlibProxyFactory extends AbstractProxyFactory {

    @Override
    @SuppressWarnings("unchecked")
    protected <T> T doCreateProxy(Class<T> interfaceClass, InvocationHandler handler) {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(interfaceClass);
        enhancer.setCallback((MethodInterceptor) (obj, method, args, proxy) -> handler.invoke(obj, method, args, () -> null));
        return (T) enhancer.create();
    }

    @Override
    @SuppressWarnings("unchecked")
    protected <T> T doCreateProxy(T target, InvocationHandler handler) {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(target.getClass());
        enhancer.setCallback((MethodInterceptor) (obj, method, args, proxy) -> handler.invoke(obj, method, args, () -> proxy.invokeSuper(obj, args)));
        return (T) enhancer.create();
    }
}
