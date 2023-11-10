package io.github.astro.mantis.configuration.annotation;

import io.github.astro.mantis.common.constant.KeyValues;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RemoteService {

    /**
     * RemoteService Name
     */
    String value();

    /**
     * 代理方式
     */
    String proxy() default KeyValues.ProxyFactory.CGLIB;

}
