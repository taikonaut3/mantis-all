package io.github.astro.mantis.spring.rpc;

import io.github.astro.mantis.configuration.RemoteCaller;
import io.github.astro.mantis.rpc.configuration.DefaultConsumerInvoker;

import java.lang.reflect.Method;

import static io.github.astro.mantis.common.constant.ServiceType.SPRING;

public class SpringConsumerInvoker extends DefaultConsumerInvoker {

    public SpringConsumerInvoker(Method method, RemoteCaller<?> remoteCaller) {
        super(method, remoteCaller);
    }

    @Override
    protected void parseAnnotationAfter() {
        setDirectory(SPRING);
        super.parseAnnotationAfter();
    }

}
