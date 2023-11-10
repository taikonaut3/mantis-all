package io.github.astro.mantis.configuration.annotation;

import static io.github.astro.mantis.common.constant.KeyValues.*;

public @interface Option {

    /**
     * 序列化方式
     */
    String serialize() default Serialize.KRYO;

    /**
     * 网络传输框架
     */
    String transport() default Transport.NETTY;

    /**
     * 事件分发器
     */
    String eventDispatcher() default EventDispatcher.DISRUPTOR;

    /**
     * 支持多注册中心配置
     */
    String[] registries() default {};

    /**
     * 调用拦截器
     */
    String[] interceptors() default {};

    /**
     * 协议配置 (提供方有效)
     */
    String[] protocols() default {};

}
