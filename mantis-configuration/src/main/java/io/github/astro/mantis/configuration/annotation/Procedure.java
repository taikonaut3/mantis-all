package io.github.astro.mantis.configuration.annotation;

import java.lang.annotation.*;

import static io.github.astro.mantis.common.constant.ServiceType.ProxyFactory;
import static io.github.astro.mantis.common.constant.ServiceType.Serializer;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Procedure {

    String value() default "";

    String description() default "";

    String version() default "1.0.0";

    String group() default "default";

    /**
     * 代理方式
     */
    String proxy() default ProxyFactory.CGLIB;

    /**
     * 序列化方式
     */
    String serialize() default Serializer.KRYO;

    /**
     * 支持多协议配置
     */
    String[] protocols() default {};

    /**
     * 支持多注册中心配置
     */
    String[] registries() default {};

    /**
     * 过滤器配置
     */
    String[] processors() default {};

}
