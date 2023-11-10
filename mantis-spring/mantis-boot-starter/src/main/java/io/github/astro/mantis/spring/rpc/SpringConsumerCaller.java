package io.github.astro.mantis.spring.rpc;

import io.github.astro.mantis.configuration.RemoteCaller;
import io.github.astro.mantis.rpc.DefaultConsumerCaller;

import java.lang.reflect.Method;

import static io.github.astro.mantis.common.constant.KeyValues.SPRING;

public class SpringConsumerCaller extends DefaultConsumerCaller {

    public SpringConsumerCaller(Method method, RemoteCaller<?> remoteCaller) {
        super(method, remoteCaller);
    }

    @Override
    protected void initAfter() {
        setDirectory(SPRING);
        super.initAfter();
    }

}
